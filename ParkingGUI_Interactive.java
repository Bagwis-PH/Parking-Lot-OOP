import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Import your existing backend logic
import Basics.*;
import Structure.*;
import Vehicles.*;
import Logic.*;
import Hardware.*;
import Accounts.*;

public class ParkingGUI_Interactive extends JFrame {

    // --- Backend Data ---
    private ParkingLot uscLot;
    private List<ParkingTicket> activeTickets;
    private Admin systemAdmin; // For Admin Login
    
    // --- UI Components ---
    private JTabbedPane tabbedPane;
    private JTextArea logArea;
    private JPanel customPanel; 
    
    // Map to link a Button back to a specific ParkingSpot object
    private Map<JButton, ParkingSpot> buttonSpotMap;

    public ParkingGUI_Interactive() {
        // 1. Initialize Data
        uscLot = new ParkingLot("USC Mega Complex");
        activeTickets = new ArrayList<>();
        buttonSpotMap = new HashMap<>();
        
        // Setup Admin Account
        Address adminAddr = new Address("Talamban", "Cebu", "6000", "PH");
        Person adminPerson = new Person("Chief Admin", adminAddr, "admin@usc.edu", "0917-123");
        systemAdmin = new Admin("admin", "password123", adminPerson);

        // 2. Setup Window
        setTitle("USC Interactive Parking System");
        setSize(1100, 500); // Shorter window since we only have 2 rows now
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 3. Build Layout
        createHeader();
        createTabbedFloors(); 
        createSidePanel();
        createLogArea();

        setVisible(true);
    }

    // --- A. BUILDING THE TABS (UP / DOWN LAYOUT) ---
    private void createTabbedFloors() {
        tabbedPane = new JTabbedPane();

        // 1. Car Section (2 rows, 10 columns = 20 spots)
        JPanel carPanel = createGridPanel(2, 10, "C", VehicleType.CAR, ParkingSpotType.COMPACT);
        tabbedPane.addTab("Car Aisle", new JScrollPane(carPanel));

        // 2. Motorbike Section (2 rows, 15 columns = 30 spots)
        JPanel bikePanel = createGridPanel(2, 15, "M", VehicleType.MOTORBIKE, ParkingSpotType.MOTORBIKE);
        tabbedPane.addTab("Motorbike Aisle", new JScrollPane(bikePanel));

        // 3. Truck/Large Section (2 rows, 5 columns = 10 spots)
        JPanel truckPanel = createGridPanel(2, 5, "L", VehicleType.TRUCK, ParkingSpotType.LARGE);
        tabbedPane.addTab("Truck/Large Aisle", new JScrollPane(truckPanel));

        // 4. Custom/VIP Section (Starts Empty)
        customPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        customPanel.setBorder(BorderFactory.createTitledBorder("VIP / Custom Spots"));
        tabbedPane.addTab("Custom / VIP Area", new JScrollPane(customPanel));

        add(tabbedPane, BorderLayout.CENTER);
    }

    // Helper to generate the grids automatically
    private JPanel createGridPanel(int rows, int cols, String prefix, VehicleType vType, ParkingSpotType sType) {
        // Changed to use fixed 2 rows (Up/Down layout)
        JPanel panel = new JPanel(new GridLayout(rows, cols, 10, 30)); // 30px gap between top/bottom row
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Loop to create buttons
        for (int i = 1; i <= (rows * cols); i++) {
            String id = prefix + "-" + i;
            
            // 1. Create Backend Spot
            ParkingSpot spot;
            if (sType == ParkingSpotType.COMPACT) spot = new CompactSpot(id);
            else if (sType == ParkingSpotType.MOTORBIKE) spot = new MotorbikeSpot(id);
            else spot = new LargeSpot(id);
            
            // 2. Create UI Button
            JButton btn = new JButton(id);
            btn.setBackground(new Color(144, 238, 144)); // Light Green
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            btn.setPreferredSize(new Dimension(80, 50)); // Rectangular spots
            
            // 3. Link them
            buttonSpotMap.put(btn, spot);

            // 4. Add Click Action (The "Click to Park" logic)
            btn.addActionListener(e -> handleSpotClick(btn, spot, vType));
            
            panel.add(btn);
        }
        return panel;
    }

    // --- B. THE INTERACTION LOGIC ---
    
    private void handleSpotClick(JButton btn, ParkingSpot spot, VehicleType allowedType) {
        // CASE 1: Spot is FREE -> User wants to PARK
        if (spot.isFree()) {
            
            // 1. Ask for Name
            String name = JOptionPane.showInputDialog(this, "Enter Driver Name to reserve spot " + spot.getSpotID() + ":");
            if (name == null || name.trim().isEmpty()) return; 

            // 2. Ask for Duration (The new feature)
            String durationStr = JOptionPane.showInputDialog(this, "How many hours will you park? (Enter 0 to start timer now)");
            if (durationStr == null) return; // Cancelled
            
            double hours = 0;
            try {
                hours = Double.parseDouble(durationStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid hours. Setting to 0.");
            }

            // 3. Create Vehicle
            String plate = "PLT-" + (int)(Math.random()*9999);
            Vehicle v;
            
            if (allowedType == VehicleType.CAR) v = new Car(plate);
            else if (allowedType == VehicleType.MOTORBIKE) v = new Motorbike(plate);
            else v = new Truck(plate);

            // 4. PARK IT
            spot.assignVehicle(v);
            
            // 5. Create Ticket & Apply Time Simulation
            ParkingTicket ticket = new ParkingTicket(v);
            
            // If user entered hours > 0, we "rewind" the clock so it looks like they parked earlier
            if (hours > 0) {
                ticket.simulateDuration(hours);
                
                // If they enter > 24 hours, maybe we flag it as overnight? 
                // Or you can ask a separate question: "Is this overnight?"
                // For now, let's keep it simple:
                if (hours >= 24) ticket.setOvernight(true);
            }
            
            activeTickets.add(ticket);

            // 6. Update UI
            btn.setBackground(new Color(255, 102, 102)); // Light Red
            btn.setText("<html><center>" + spot.getSpotID() + "<br>" + name + "</center></html>");
            
            log("ENTRY: " + name + " parked (" + hours + "hr sim) in " + spot.getSpotID());
            JOptionPane.showMessageDialog(this, "Welcome, " + name + "!\nTicket ID: " + ticket.getTicketID());
        } 
        
        // CASE 2: Spot is OCCUPIED
        else {
            JOptionPane.showMessageDialog(this, "Spot " + spot.getSpotID() + " is currently occupied.\nVehicle: " + spot.getVehicle().getLicensePlate());
        }
    }
        

    // --- C. SIDE CONTROLS & ADMIN ---
    private void createSidePanel() {
        JPanel side = new JPanel(new GridLayout(5, 1, 10, 10));
        side.setPreferredSize(new Dimension(200, 0));
        side.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnCustom = new JButton("Get Custom Quote");
        JButton btnPay = new JButton("Pay & Exit");
        JButton btnStats = new JButton("View Capacity");
        JButton btnAdmin = new JButton("Admin Login"); // <--- ADDED ADMIN BUTTON

        // Actions
        btnCustom.addActionListener(e -> handleCustomQuote());
        btnPay.addActionListener(e -> handleExit());
        btnAdmin.addActionListener(e -> handleAdminLogin()); // <--- ADDED ACTION
        btnStats.addActionListener(e -> log("System: " + activeTickets.size() + " active tickets."));

        side.add(new JLabel("Control Panel", SwingConstants.CENTER));
        side.add(btnCustom);
        side.add(btnPay);
        side.add(btnStats);
        side.add(btnAdmin); // Add to panel

        add(side, BorderLayout.EAST);
    }

    // --- D. CUSTOM QUOTE LOGIC (VIP) ---
    private void handleCustomQuote() {
        String inputSize = JOptionPane.showInputDialog(this, "Enter Vehicle Size (sq meters):");
        if (inputSize == null) return;
        
        try {
            double size = Double.parseDouble(inputSize);
            double price = PricingStrategy.calculateCustomQuote(size);
            
            int choice = JOptionPane.showConfirmDialog(this, 
                "Quote: PHP " + price + "/hr.\nDo you want to book a spot?", 
                "Custom Quote", JOptionPane.YES_NO_OPTION);
            
            if (choice == JOptionPane.YES_OPTION) {
                String name = JOptionPane.showInputDialog(this, "Enter Driver Name:");
                createCustomSpot(name, size);
            }
        } catch (NumberFormatException ex) {
            log("Error: Invalid size entered.");
        }
    }

    private void createCustomSpot(String name, double size) {
        String id = "VIP-" + (customPanel.getComponentCount() + 1);
        ParkingSpot spot = new LargeSpot(id); // Custom spots count as Large backend-wise
        
        // Create the Visual Button
        JButton btn = new JButton("<html><center>" + id + "<br>" + name + "<br>" + size + "m²</center></html>");
        btn.setPreferredSize(new Dimension(100, 80));
        btn.setBackground(new Color(255, 102, 102)); // Already occupied (Red)
        btn.setForeground(Color.WHITE);
        
        // Auto-park the vehicle
        Vehicle v = new Truck("VIP-PLT"); 
        spot.assignVehicle(v);
        activeTickets.add(new ParkingTicket(v));
        
        // *** CRITICAL: Add to map so we can exit later ***
        buttonSpotMap.put(btn, spot); 

        // Add to GUI
        customPanel.add(btn);
        tabbedPane.setSelectedIndex(3); // Switch to VIP tab
        
        customPanel.revalidate();
        customPanel.repaint();
        
        log("CUSTOM: Created VIP spot " + id + " for " + name);
    }

    // --- E. EXIT LOGIC (Universal) ---
    private void handleExit() {
        if (activeTickets.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No vehicles inside!");
            return;
        }

        String[] ticketList = new String[activeTickets.size()];
        for (int i = 0; i < activeTickets.size(); i++) {
            ticketList[i] = activeTickets.get(i).getTicketID();
        }

        String picked = (String) JOptionPane.showInputDialog(this, "Select Ticket to Pay:", 
                "Exit", JOptionPane.QUESTION_MESSAGE, null, ticketList, ticketList[0]);
        
        if (picked != null) {
            ParkingTicket tToRemove = null;
            for(ParkingTicket t : activeTickets) {
                if(t.getTicketID().equals(picked)) tToRemove = t;
            }

            if(tToRemove != null) {
                tToRemove.closeTicket();
                JOptionPane.showMessageDialog(this, "Total Fee: PHP " + tToRemove.getTotalFee() + "\nPaid. Gate Open.");
                
                resetButtonVisuals(tToRemove);
                
                activeTickets.remove(tToRemove);
                log("EXIT: Ticket " + picked + " processed.");
            }
        }
    }

    private void resetButtonVisuals(ParkingTicket t) {
        // Find which button holds this vehicle
        JButton buttonToRemove = null;

        for (Map.Entry<JButton, ParkingSpot> entry : buttonSpotMap.entrySet()) {
            ParkingSpot spot = entry.getValue();
            // Check if this spot holds the vehicle from the ticket
            if (!spot.isFree() && spot.getVehicle() == t.getVehicle()) { 
                 
                 spot.removeVehicle();
                 JButton btn = entry.getKey();

                 // If it's a VIP spot, we might want to remove the button entirely?
                 // For now, let's just turn it Green and reset text
                 btn.setBackground(new Color(144, 238, 144)); 
                 btn.setText(spot.getSpotID()); 
                 btn.setForeground(Color.BLACK);
                 
                 // If it was in the Custom Panel, let's actually remove it to clear space
                 if (spot.getSpotID().startsWith("VIP-")) {
                     buttonToRemove = btn;
                 }
                 break;
            }
        }

        // Special handling for VIP spots: Remove the button completely on exit
        if (buttonToRemove != null) {
            customPanel.remove(buttonToRemove);
            customPanel.revalidate();
            customPanel.repaint();
            buttonSpotMap.remove(buttonToRemove); // Clean up map
        }
    }

    // --- F. ADMIN LOGIN LOGIC ---
    private void handleAdminLogin() {
        JPanel p = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        
        p.add(new JLabel("Username:")); p.add(userField);
        p.add(new JLabel("Password:")); p.add(passField);

        int res = JOptionPane.showConfirmDialog(this, p, "Admin Login", JOptionPane.OK_CANCEL_OPTION);
        
        if (res == JOptionPane.OK_OPTION) {
            String u = userField.getText();
            String pw = new String(passField.getPassword());
            
            if (systemAdmin.login(u, pw)) {
                // Show Admin Info
                Person per = systemAdmin.getPerson();
                String info = "ADMIN DASHBOARD\n\n" +
                              "Name: " + per.getName() + "\n" +
                              "Email: " + per.getEmail() + "\n" +
                              "Address: " + per.getAddress().toString() + "\n\n" +
                              "Active Cars: " + activeTickets.size();
                JOptionPane.showMessageDialog(this, info, "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                log("ADMIN: Logged in successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
                log("ADMIN: Login failed.");
            }
        }
    }

    // --- UI HELPERS ---
    private void createHeader() {
        JLabel l = new JLabel("USC PARKING: SELECT A SPOT TO PARK", SwingConstants.CENTER);
        l.setFont(new Font("Arial", Font.BOLD, 20));
        l.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(l, BorderLayout.NORTH);
    }
    
    private void createLogArea() {
        logArea = new JTextArea(5, 50);
        add(new JScrollPane(logArea), BorderLayout.SOUTH);
    }
    
    private void log(String s) { logArea.append(s + "\n"); }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        new ParkingGUI_Interactive();
    }
}
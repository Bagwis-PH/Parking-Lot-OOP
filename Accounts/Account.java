package Accounts;

public class Account {
    private String username;
    private String password;
    private Person person; // Composition: Account HAS-A Person info
    private boolean isActive;

    public Account(String username, String password, Person person) {
        this.username = username;
        this.password = password;
        this.person = person;
        this.isActive = true;
    }

    // Basic Login check
    public boolean login(String inputUser, String inputPass) {
        return this.username.equals(inputUser) && this.password.equals(inputPass);
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    // We usually don't have a getPassword() for security, but we can set it.
    public void setPassword(String password) { this.password = password; }

    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}

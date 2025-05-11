package ma.est.carrentalnm;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class SeleniumTests {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setupClass() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // temps d'attente augmenté
    }

    @BeforeEach
    public void openLoginPage() {
        driver.get("http://localhost:5175/login"); // adapte selon ton routeur React
    }

    public void LoginAdmin() {
        // Remplir l'email
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        emailField.sendKeys("Nouhaila@gmail.com");

        // Remplir le mot de passe
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("123");

        // Soumettre le formulaire
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.name("submit")));
        submitButton.click();

        // Attendre et cliquer sur "Dashboard"
        WebElement dashboardLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("dashboard")));
        dashboardLink.click();
    }

    @Test
    public void LoginClient() {
        // Remplir l'email
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        emailField.sendKeys("moncef@gmail.com");

        // Remplir le mot de passe
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("123");

        // Soumettre le formulaire
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.name("submit")));
        submitButton.click();

        WebElement homeLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("home")));
        homeLink.click();
    }

    public void carList(){
        WebElement carsLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Cars list")));
        carsLink.click();
    }


    @Test
    public void testAddCar() throws InterruptedException {
        LoginAdmin();
        carList();

        // Cliquer sur le bouton "Ajouter"
        WebElement addCarButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("Ajouter")));
        addCarButton.click();

        driver.findElement(By.name("name")).sendKeys("Toyota Corolla");
        driver.findElement(By.name("seats")).sendKeys("5");

        // Sélectionner la transmission
        Select transmissionSelect = new Select(driver.findElement(By.name("transmission")));
        transmissionSelect.selectByVisibleText("Automatique");

        // Sélectionner le carburant
        Select carburantSelect = new Select(driver.findElement(By.name("fuel")));
        carburantSelect.selectByVisibleText("Essence");

        driver.findElement(By.name("power")).sendKeys("90");
        driver.findElement(By.name("price")).sendKeys("200000");

        // Sélectionner le type
        Select typeSelect = new Select(driver.findElement(By.name("type")));
        typeSelect.selectByVisibleText("Location");

        // Uploader une image (chemin absolu requis)
        WebElement fileInput = driver.findElement(By.name("image"));
        fileInput.sendKeys("C:\\Users\\monce\\Downloads\\v.jpeg"); // Mets un chemin valide ici

        // Soumettre le formulaire
        WebElement submitBtn = driver.findElement(By.xpath("//button[contains(text(), 'Ajouter la voiture')]"));
        submitBtn.click();

        // Attendre l'alerte
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

// Vérifier le texte de l'alerte
        String alertText = alert.getText();
        assertEquals("Voiture ajoutée avec succès!", alertText);

// Fermer l'alerte
        alert.accept();
        driver.navigate().back();
    }

    @Test
    public void testAddCarEmpty() throws InterruptedException {
        LoginAdmin(); // connexion
        carList();   // aller vers la liste des voitures

        WebElement addCarButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("Ajouter")));
        addCarButton.click();

        driver.findElement(By.name("name")).sendKeys("Toyota Corolla");
        driver.findElement(By.name("seats")).sendKeys("5");

        Select transmissionSelect = new Select(driver.findElement(By.name("transmission")));
        transmissionSelect.selectByVisibleText("Automatique");

        Select carburantSelect = new Select(driver.findElement(By.name("fuel")));
        carburantSelect.selectByVisibleText("Essence");

        driver.findElement(By.name("power")).sendKeys("90");

        Select typeSelect = new Select(driver.findElement(By.name("type")));
        typeSelect.selectByVisibleText("Location");

        WebElement fileInput = driver.findElement(By.name("image"));
        fileInput.sendKeys("C:\\Users\\monce\\Downloads\\v.jpeg");

        // Vérifier que tous les champs sont bien remplis
        assertFalse(driver.findElement(By.name("name")).getAttribute("value").isEmpty());
        assertFalse(driver.findElement(By.name("seats")).getAttribute("value").isEmpty());
        assertEquals("Automatique", transmissionSelect.getFirstSelectedOption().getText());
        assertEquals("Essence", carburantSelect.getFirstSelectedOption().getText());
        assertFalse(driver.findElement(By.name("power")).getAttribute("value").isEmpty());
        assertFalse(driver.findElement(By.name("price")).getAttribute("value").isEmpty());
        assertEquals("Location", typeSelect.getFirstSelectedOption().getText());
        assertFalse(fileInput.getAttribute("value").isEmpty());

        // Soumettre
        WebElement submitBtn = driver.findElement(By.xpath("//button[contains(text(), 'Ajouter la voiture')]"));
        submitBtn.click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        assertEquals("Voiture ajoutée avec succès!", alertText);
        alert.accept();
        driver.navigate().back();
    }


    @Test
    public void testUpdateCar() {
        LoginAdmin();
        carList();

        // Cliquer sur le bouton "Modifier" (à adapter selon le contenu de la ligne)
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//td[contains(text(),'Renault Clio 2025')]/following-sibling::td//button[contains(text(),'Modifier')]")
        ));
        editButton.click();

        // Modifier tous les champs
        WebElement modelInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
        modelInput.clear();
        modelInput.sendKeys("Renault Clio 2025");

        WebElement seatsInput = driver.findElement(By.name("seats"));
        seatsInput.clear();
        seatsInput.sendKeys("5");

        WebElement transmissionSelect = driver.findElement(By.name("transmission"));
        new Select(transmissionSelect).selectByVisibleText("Automatique");

        WebElement fuelSelect = driver.findElement(By.name("fuel"));
        new Select(fuelSelect).selectByVisibleText("Essence");

        WebElement powerInput = driver.findElement(By.name("power"));
        powerInput.clear();
        powerInput.sendKeys("90 ch");

        WebElement typeSelect = driver.findElement(By.name("type"));
        new Select(typeSelect).selectByVisibleText("Vente");

        WebElement priceInput = driver.findElement(By.name("price"));
        priceInput.clear();
        priceInput.sendKeys("150000");

        WebElement imageInput = driver.findElement(By.name("image"));
        imageInput.sendKeys("C:\\Users\\monce\\Downloads\\v.jpeg"); // Change ce chemin vers une image réelle

        // Soumettre le formulaire
        WebElement updateButton = driver.findElement(By.xpath("//button[contains(text(),'Mettre à jour la voiture')]"));
        updateButton.click();

        // Gérer l'alerte de succès
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        assertEquals("Voiture mise à jour avec succès!", alert.getText());
        alert.accept();
    }

    @Test
    public void testDeleteCarNotReserved() {
        LoginAdmin();
        carList();

        // Locate and click page 6 using JavaScript
        WebElement page6Btn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//ul//li/button[text()='6']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", page6Btn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", page6Btn);

        // Wait for content of page 6 to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//table//tr[1]//td[contains(text(),'Toyota Corolla')]")));

        // Click delete
        WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//table//tr[1]//button[contains(text(),'Supprimer')]")));
        deleteBtn.click();

        // Confirm deletion
        WebElement confirmPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(text(),'Êtes-vous sûr de vouloir supprimer cette voiture')]")));
        WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("confirmerSuppression")));
        confirmBtn.click();

        // Assert success
        WebElement successAlert = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Voiture supprimée avec succès!')]")));
        assertEquals("Voiture supprimée avec succès!", successAlert.getText());
    }

    @Test
    public void testLoginNavigation() {
// Remplir l'email
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        emailField.sendKeys("Nouhaila@gmail.com");

        // Remplir le mot de passe
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("123");

        // Soumettre le formulaire
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.name("submit")));
        submitButton.click();


        // Wait for the URL to change to the expected admin URL
        wait.until(ExpectedConditions.urlToBe("http://localhost:5174/admin"));

        // Get the current URL after redirection
        String currentUrl = driver.getCurrentUrl();

        // Assert that the current URL is the expected admin URL
        assertEquals("http://localhost:5174/admin", currentUrl, "Login should redirect to the /admin page");
    }

    @Test
    public void testCarSearch() {
        LoginAdmin();
        carList();
        // Wait for search input and enter search term
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[placeholder='Rechercher par nom...']")));
        searchInput.sendKeys("Peugeot");

        // Wait for filtered result (give time for re-rendering)
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td[contains(text(), 'Peugeot')]")));

        // Get visible rows
        int visibleRows = driver.findElements(
                By.xpath("//tbody/tr[td[contains(text(), 'Peugeot')]]")).size();

        // Assert that at least one result is shown
        assert visibleRows > 0 : "No Peugeot cars shown in search results.";
    }


    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

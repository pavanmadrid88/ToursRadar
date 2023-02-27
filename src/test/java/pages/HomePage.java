package pages;

import framework.BasePage;

import org.openqa.selenium.By;

public class HomePage extends BasePage {

    By adventure_title_image = By.cssSelector("h2[class*='adventure-hero-flexible-slider__title']");

    public By getAdventure_title_image() {
        return adventure_title_image;
    }
}

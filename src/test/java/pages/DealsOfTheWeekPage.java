package pages;

import framework.BasePage;
import org.openqa.selenium.By;

public class DealsOfTheWeekPage extends BasePage {

        By dealsOfTheWeekHeading = By.cssSelector("h1[class*='clp-sale-hero__heading'");

        public Boolean isDealsOfTheWeekPageDisplayed(){
            return isElementDisplayed(dealsOfTheWeekHeading,20);
        }


}

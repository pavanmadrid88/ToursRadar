package pages;

import framework.BaseClass;
import framework.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EuropeToursPage extends BasePage {

    By homeLink = By.cssSelector("a[data-cy = 'common-header--home-link']");
    By topBannerLink = By.cssSelector("div[class*='common-sale-top-banner']");

    By filterCheckboxes = By.cssSelector("label[class*='filters-checkbox']");

    By private_travel_style_link = By.cssSelector("dl a[href*='private']");

    By group_travel_style_link = By.cssSelector("dl a[href*='group']");

    By toursListImages = By.cssSelector("img[data-id]");

    By toursDetailsLink = By.cssSelector("a[class*='tourLink'] h4");
    Logger logger = LoggerFactory.getLogger(BaseClass.class);


    public HomePage clickHomeLink(){
        clickElement(homeLink);
        return new HomePage();
    }

    public DealsOfTheWeekPage clickTopBannerLink(){

        clickElement(topBannerLink);
        return new DealsOfTheWeekPage();
    }

    public Boolean selectFilterCheckBox(String filterType){
        logger.info("Selecting Filter Checkbox of Type :" + filterType);
        boolean selection_status = false;
        List<WebElement> checkboxElements = threadLocalWebDriver.get().findElements(filterCheckboxes);
        for(WebElement checkboxElement:checkboxElements){
            String text = checkboxElement.getText();
            if(text.trim().toUpperCase().equals(filterType.toUpperCase().trim())){
                checkboxElement.click();
                selection_status = true;
                logger.info("Selected Filter Checkbox of Type :" + filterType);
                break;
            }
        }
        return selection_status;

    }

    public int getCountOfToursOnPage(){
        return getElementSize(toursListImages);
    }

    public int getCountOfPrivateToursLinks(){
        return getElementSize(private_travel_style_link);
    }

    public int getCountOfGroupToursLinks(){
        return getElementSize(group_travel_style_link);
    }

    public boolean clickTour(String tourText){
        boolean tourSelection = false;
        logger.info("Clicking on tour:" + tourText);
        List<WebElement> tours = threadLocalWebDriver.get().findElements(toursDetailsLink);
        for(WebElement tour:tours){
            String current_tour_text = tour.getText();
            if(current_tour_text.trim().equalsIgnoreCase(tourText.trim())) {
                tour.click();
                logger.info("Clicked on Tour :" + tourText);
                tourSelection = true;
            }
        }
        return tourSelection;
    }



}

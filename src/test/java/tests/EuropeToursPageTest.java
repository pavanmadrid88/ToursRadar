package tests;
import config.DataConstants;
import framework.BaseClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DealsOfTheWeekPage;
import pages.EuropeToursPage;
import pages.HomePage;


public class EuropeToursPageTest extends BaseClass {

    EuropeToursPage europeToursPage;
    HomePage homePage;

    DealsOfTheWeekPage dealsOfTheWeekPage;
    Logger log = LoggerFactory.getLogger(EuropeToursPageTest.class);



    @BeforeMethod
    public void europeToursBeforeMethod(){
        europeToursPage = new EuropeToursPage();
    }

    @Test
    public void validateEuropeToursPageLanding(){
        log.info("*****Starting Test - Europe Tours Page Landing test********");
        assert getPageHeaderText().equals(DataConstants.PageHeaderTexts.EUROPE_TOURS) && getPageTitle().equals(DataConstants.WindowTitles.EUROPE_TOURS);
        log.info("ELEMENTS Dashboard Page loaded!");
        log.info("*****Ending Test - Europe Tours Page Landing test********");

    }

    @Test
    public void validateHomePageRedirectionFromHeader(){
        log.info("Starting Test  - Validate Home page redirection from header logo");
        homePage =  europeToursPage.clickHomeLink();
        assert isElementDisplayed(homePage.getAdventure_title_image(), Long.parseLong(properties.getProperty("timeout")));
        log.info("Ending Test  - Validate Home page redirection from header logo");

    }

    @Test
    public void validateDealsOfTheWeek(){
        log.info("Starting Test  - Validate Deals of the week redirection");
        dealsOfTheWeekPage = europeToursPage.clickTopBannerLink();
        assert dealsOfTheWeekPage.isDealsOfTheWeekPageDisplayed();
        log.info("Ending Test  - Validate Deals of the week redirection");

    }

    @Test
    public void validateSetFilterAdventureTypeTest(){
        log.info("Starting Test  - Validate Adventure Type filter selection Test");
        assert europeToursPage.selectFilterCheckBox(DataConstants.FilterAdventureTypes.GROUP);
        int count_of_tours_displayed_on_page = europeToursPage.getCountOfToursOnPage();
        int count_of_private_tours_links = europeToursPage.getCountOfGroupToursLinks();
        log.info("Size of Tours on page:" + count_of_tours_displayed_on_page);
        log.info("Size of Private links on tours:"  + count_of_private_tours_links);
        assert count_of_tours_displayed_on_page == count_of_private_tours_links ;
        log.info("Ending Test  - Validate Adventure Type filter selection Test");

    }

    @Test
    public void validateTourDetailsRedirectionTest(){
        log.info("Starting Test  - Validate Tours Details redirection Test");
        assert europeToursPage.clickTour(DataConstants.TourTitleTexts.GENUINE_EUROPE);
        assert getWindowHandleSize() > 1;
        switchToLatestWindowHandle();
        assert getPageTitle().trim().toUpperCase().contains(DataConstants.TourTitleTexts.GENUINE_EUROPE.toUpperCase());
        log.info("Ending Test  - Validate Tours Details redirection Test");

    }




}

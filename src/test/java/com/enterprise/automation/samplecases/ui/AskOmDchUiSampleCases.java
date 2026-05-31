package com.enterprise.automation.samplecases.ui;

import com.enterprise.automation.annotations.UiTest;
import com.enterprise.automation.tests.BaseTest;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@UiTest
@Tag("sample-reference")
class AskOmDchUiSampleCases extends BaseTest {
    private static final String SAMPLE_UI_BASE_URL =
            System.getProperty("sample.ui.baseUrl", "https://askomdch.com");

    @Test
    void homePageDisplaysFeaturedProducts() {
        page.navigate(SAMPLE_UI_BASE_URL);

        assertTrue(page.title().contains("AskOmDch"), "Home page title should contain AskOmDch");
        page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Featured Products")).waitFor();
        page.getByText("Blue Shoes").first().waitFor();
    }

    @Test
    void userCanOpenAProductDetailsPage() {
        page.navigate(SAMPLE_UI_BASE_URL);

        page.getByText("Blue Shoes").first().click();

        page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Blue Shoes")).waitFor();
        assertTrue(page.url().contains("blue-shoes"), "Product URL should contain the product slug");
    }
}

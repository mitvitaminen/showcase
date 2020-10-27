package net.croware.showcase;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.flow.theme.lumo.Lumo;

public class MainViewIT extends AbstractViewTest {

    @Test
    public void buttonIsUsingLumoTheme() {
        final WebElement element = $(ButtonElement.class).first();
        assertThemePresentOnElement(element, Lumo.class);
    }

    @Test
    public void clickingButtonShowsNotification() {
        Assert.assertFalse($(NotificationElement.class).exists());
        $(ButtonElement.class).first().click();
        Assert.assertTrue($(NotificationElement.class).waitForFirst().isOpen());
    }

    @Test
    public void clickingButtonTwiceShowsTwoNotifications() {
        Assert.assertFalse($(NotificationElement.class).exists());
        final ButtonElement button = $(ButtonElement.class).first();
        button.click();
        button.click();
        Assert.assertEquals(2, $(NotificationElement.class).all().size());
    }

    @Test
    public void testClickButtonShowsHelloAnonymousUserNotificationWhenUserNameIsEmpty() {
        final ButtonElement button = $(ButtonElement.class).first();
        button.click();
        Assert.assertTrue($(NotificationElement.class).exists());
        final NotificationElement notification = $(NotificationElement.class).first();
        Assert.assertEquals("Hello anonymous user", notification.getText());
    }

    @Test
    public void testClickButtonShowsHelloUserNotificationWhenUserIsNotEmpty() {
        final TextFieldElement textField = $(TextFieldElement.class).first();
        textField.setValue("Vaadiner");
        final ButtonElement button = $(ButtonElement.class).first();
        button.click();
        Assert.assertTrue($(NotificationElement.class).exists());
        final NotificationElement notification = $(NotificationElement.class).first();
        Assert.assertEquals("Hello Vaadiner", notification.getText());
    }

    @Test
    public void testEnterShowsHelloUserNotificationWhenUserIsNotEmpty() {
        final TextFieldElement textField = $(TextFieldElement.class).first();
        textField.setValue("Vaadiner");
        textField.sendKeys(Keys.ENTER);
        Assert.assertTrue($(NotificationElement.class).exists());
        final NotificationElement notification = $(NotificationElement.class).first();
        Assert.assertEquals("Hello Vaadiner", notification.getText());
    }
}

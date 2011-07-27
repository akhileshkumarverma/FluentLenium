FluentLenium is a framework which provide help when writting Selenium Web Driver test with jUnit.
You can use the framework of assertion you want, classical jUnit assertions, hamcrest or as in the following examples fest-assert.


## Cross Browser
FluentLenium can support all the browser available in Selenium WebDriver.
Wich means :
    * FireFox
    * Internet Explorer
    * Google Chrome
    * Opera

But there is also some project working on android etc ...

## Basic

Launching the first test.
Your Test Class may extend fr.java.freelance.fluentlenium.core.test.FluentTest By default, it use FirefoxWebDriver to launch all tests.
After that, all your test methods will be able to be tested into a browser.
The syntaxes offers directly by Selenium are quite a bit too verbose. FluentLenium proposes two differents syntaxes to easily launch tests.
For examples, if you want to go to an adresse, fill a form and click to a button to submit and tests that the title of the page have changed
<pre><code>
goTo("http://mywebpage/");
fill("#firstName").with("toto");
click("#create-button");
assertThat(title()).isEqualTo("Hello toto");
</code></pre>

If you are more convenient to the JQuery Syntax, maybe something like that will be more natural for you:
<pre><code>goTo("http://mywebpage/");
$("#firstName").text("toto");
$("#create-button").click();
assertThat(title()).isEqualTo("Hello toto");</code></pre>

Both syntax are equivalent. $ or find methods are aliases.

### JQuery Selector
#### Default Selector
     You can use CSS1, CSS2 and CSS3 selector with the same restrictions as in Selenium.

     If you want to find the list of elements which have
     the id "title" : find("#title") or $("#title")
     the class name "small" : find(".small") or $(".small")
     the tag name "input" : find("input") or $("input")

     You are free to use most of the CSS3 syntax, wich means that
     find("input[class=rightForm]") or $("input[class=rightForm]")
     will return the list of all input elements which have the class rightForm

#### Custom filter
    But what if you want all the input that have a text equals to "Sam" ?
    You can use filters to allow that kind of search. For example :
<pre><code>
     find(".small",withName("foo"))
     find(".small",withId("idOne"))
     find(".small",withText("This field is mandatory."))
</code></pre>

     You can also chained filters :
     find(".small",withName("foo"),withId("id1")) will return all the elements matching the 3 criterias : - class .small ,id id1 and name foo.

     If you want others precisions that just the css selector, just use our filters features.
     For now, you have two differents filters :
     - contains
     - notContains

     For each of them, you can choose to use a css selector :
<pre><code>
     find(".small", withName(notContains("name"))
     find(".small", withId(notContains("id"))
     find(".small", withText(notContains("Female")))
</code></pre>

     Or to be more precise, you can choose to use a regexp :
<pre><code>
     find(".small", withName(contains(regex("na?me[0-9]*")))
     find(".small", withName(notContains(regex("na?me[0-9]*")))
</code></pre>

     Of course, if you are a regexp jedi, that's not needed !

     More will come soon to filter to create a complete search tool (startsWith,endsWith...).

### N-th
     If you want the first elements that matchs your criteria, just use :
<pre><code>
     findFirst(myCssSelector) or $(myCssSelector).first()
</code></pre>

     If you want the element at the given position :
<pre><code>
     find(myCssSelector,2) or $(myCssSelector,2)
</code></pre>

     Of course, you can use both position filter and custom filter :
<pre><code>
     find(myCssSelector,2,withName("foo"))
</code></pre>

#### Find on children
     You can also chained the find call :
     find(myCssSelector).find("input") will return all the web element input into the css selector tree.
     You can add more indication :
<pre><code>
     find(myCssSelector,2,withName("foo")).find("input",withName("bar"))
     or find(myCssSelector,2,withName("foo")).findFirst("input",withName("bar"))
</code></pre>

## Form Action

    If you need to click, fill, submit or clean an element or a list of element, just go naturally for it.
### Fill
    fill("input").with("bar") OR $("input").text("bar") will fill all the element with tag input with bar.
     Don't forget, only the visible field will be modified. It simulates your action in a browser !

#### Click

       click("#create-button") is equivalent to $("#create-button").click().
       It will click on all the visible fields returned by the search.

#### Clear
       clear("#create-button") is equivalent to $("#create-button").clear();
       It will clear  all the visible fields returned by the search.

#### Submit
       submit("#create-button") is equivalent to $("#create-button").submit();
       It will submit all the visible fields returned by the search.


  //TODO Add more infos there
## Page Object pattern
Because Selenium test can easily become a mess, Page Object Pattern are a recommanded Pattern when writing automatised integration test.
Page Pattern will inclosing all the pumbling, which make tests a lot easier to read and to maintain.

Try to construct your Page thinking that it is better if you offert services from your page rather that just the internals of the page.
A Page Object can modelized the whole page or just a part of it.

To construct a Page, it have to extends fr.java.freelance.fluentlenium.core.FluentPage.
In most of the cases, you have to defined the url of the page with overriding the getUrl methods.
In that way, you can go in your test to that page with goTo(myPage)

To control that you are in the good page, not only the url [accessible in your test via the void url() method] can be needed.
Redefined the isAt methods to list all the assertions you have to make in order to be sure that you are in the good pages.
For exemple, if I choose that the title will be sufficient to know if I'm in the page :

<pre><code>
        @Override
        public void isAt() {
            assertThat($("title").first().getText()).contains("Selenium");
        }
</code></pre>

Create you owm methods to easily fill form, go to a next page or whatesle can be needed in your test.

For exemple :
<pre><code>
public class LoginPage extends FluentPage {
     public LoginPage(WebDriver driver) {
        super(driver);
    }
    public String getUrl() {
        return "myCustomUrl";
    }
    public void isAt() {
        assertThat($("title").first().getText()).isEqualTo("MyTitle");
    }
    public void fillAndSubmitForm(String... paramsOrdered) {
        fill("input").with(paramsOrdered);
        $("#create-button").click();
    }
}
</code></pre>

And the corresponding test :
<pre><code>
public void checkLoginFailed() {
 goTo(loginPage);
 loginPage.fillAndSubmitLoginForm("login","wrongPass");
  assertThat($(".error")).hasSize(1);
}
</code></pre>

## Custom Driver
If you need to change your driver, just override the getDefaultDriver in your test. You can use every driver

## FluentLenium and others framework
### jUnit
FluentLenium used jUnit by default. You can use test using jUnit assertions, but can of course use others frameworks, more fluent, as Fluent-assert or Hamcrest.

<pre><code>
 goTo("http://mywebpage/");
 fill("#firstName").with("toto");
 click("#create-button");
 assertEqual("Hello toto",title());
</code></pre>

### Fest-Assert
<pre><code>
goTo("http://mywebpage/");
fill("#firstName").with("toto");
click("#create-button");
assertThat(title()).isEqualTo("Hello toto");
</code></pre>

### Hamcrest
<pre><code>
 goTo("http://mywebpage/");
 fill("#firstName").with("toto");
 click("#create-button");
 assertThat(title(),equalTo("Hello toto"));
</code></pre>

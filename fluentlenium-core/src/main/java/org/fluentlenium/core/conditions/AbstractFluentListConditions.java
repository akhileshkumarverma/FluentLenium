package org.fluentlenium.core.conditions;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.fluentlenium.core.domain.FluentWebElement;

import java.util.List;

/**
 * Abstract class conditions on list of elements.
 */
public abstract class AbstractFluentListConditions implements FluentListConditions {
    private boolean negation;

    private final List<? extends FluentWebElement> elements;

    protected AbstractFluentListConditions(final List<? extends FluentWebElement> elements) {
        this.elements = elements;
    }

    @Override
    public boolean size(final int size) {
        if (negation) {
            return elements.size() != size;
        }
        return elements.size() == size;
    }

    protected boolean isNegation() {
        return negation;
    }

    public void setNegation(final boolean negation) {
        this.negation = negation;
    }

    protected List<? extends FluentWebElement> getElements() {
        return elements;
    }

    @Override
    public IntegerConditions size() {
        return new IntegerConditionsImpl(elements.size(), negation);
    }

    @Override
    public boolean verify(final Predicate<FluentWebElement> predicate) {
        return verify(predicate, false);
    }

    @Override
    public boolean present() {
        return verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return input.conditions().present();
            }
        }, false);
    }

    @Override
    public boolean clickable() {
        return verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return input.conditions().clickable();
            }
        }, false);
    }

    @Override
    public boolean stale() {
        return verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return input.conditions().stale();
            }
        }, false);
    }

    @Override
    public boolean displayed() {
        return verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return input.conditions().displayed();
            }
        }, false);
    }

    @Override
    public boolean enabled() {
        return verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return input.conditions().enabled();
            }
        }, false);
    }

    @Override
    public boolean selected() {
        return verify(new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return input.conditions().selected();
            }
        }, false);
    }

    @Override
    public boolean attribute(final String name, final String value) {
        return attribute(name).equalTo(value);
    }

    @Override
    public StringConditions attribute(final String name) {
        return new StringListConditionsImpl(this, new Function<FluentWebElement, String>() {
            @Override
            public String apply(final FluentWebElement input) {
                return input.attribute(name);
            }
        }, new Function<FluentWebElement, StringConditions>() {
            @Override
            public StringConditions apply(final FluentWebElement input) {
                return input.conditions().attribute(name);
            }
        });
    }

    @Override
    public boolean id(final String id) {
        return id().equalTo(id);
    }

    @Override
    public StringConditions id() {
        return new StringListConditionsImpl(this, new Function<FluentWebElement, String>() {
            @Override
            public String apply(final FluentWebElement input) {
                return input.id();
            }
        }, new Function<FluentWebElement, StringConditions>() {
            @Override
            public StringConditions apply(final FluentWebElement input) {
                return input.conditions().id();
            }
        });
    }

    @Override
    public StringConditions name() {
        return new StringListConditionsImpl(this, new Function<FluentWebElement, String>() {
            @Override
            public String apply(final FluentWebElement input) {
                return input.name();
            }
        }, new Function<FluentWebElement, StringConditions>() {
            @Override
            public StringConditions apply(final FluentWebElement input) {
                return input.conditions().name();
            }
        });
    }

    @Override
    public boolean name(final String name) {
        return name().equalTo(name);
    }

    @Override
    public StringConditions tagName() {
        return new StringListConditionsImpl(this, new Function<FluentWebElement, String>() {
            @Override
            public String apply(final FluentWebElement input) {
                return input.tagName();
            }
        }, new Function<FluentWebElement, StringConditions>() {
            @Override
            public StringConditions apply(final FluentWebElement input) {
                return input.conditions().tagName();
            }
        });
    }

    @Override
    public boolean tagName(final String tagName) {
        return tagName().equalTo(tagName);
    }

    @Override
    public StringConditions value() {
        return new StringListConditionsImpl(this, new Function<FluentWebElement, String>() {
            @Override
            public String apply(final FluentWebElement input) {
                return input.value();
            }
        }, new Function<FluentWebElement, StringConditions>() {
            @Override
            public StringConditions apply(final FluentWebElement input) {
                return input.conditions().value();
            }
        });
    }

    @Override
    public boolean value(final String value) {
        return value().equalTo(value);
    }

    @Override
    public StringConditions text() {
        return new StringListConditionsImpl(this, new Function<FluentWebElement, String>() {
            @Override
            public String apply(final FluentWebElement input) {
                return input.text();
            }
        }, new Function<FluentWebElement, StringConditions>() {
            @Override
            public StringConditions apply(final FluentWebElement input) {
                return input.conditions().text();
            }
        });
    }

    @Override
    public boolean text(final String anotherString) {
        return text().equalTo(anotherString);
    }

    @Override
    public StringConditions textContent() {
        return new StringListConditionsImpl(this, new Function<FluentWebElement, String>() {
            @Override
            public String apply(final FluentWebElement input) {
                return input.textContent();
            }
        }, new Function<FluentWebElement, StringConditions>() {
            @Override
            public StringConditions apply(final FluentWebElement input) {
                return input.conditions().textContent();
            }
        });
    }

    @Override
    public boolean textContent(final String anotherString) {
        return textContent().equalTo(anotherString);
    }

    @Override
    public RectangleConditions rectangle() {
        return new RectangleListConditionsImpl(this);
    }
}

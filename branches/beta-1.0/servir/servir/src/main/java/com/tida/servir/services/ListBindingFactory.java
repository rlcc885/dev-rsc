package com.tida.servir.services;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ioc.Location;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.BindingSource;

/**
 * Factory for list bindings. List bindings parse comma-delimited lists of binding expressions into {@link List lists}
 * of values. The default binding prefix for each binding expression is prop.
 */
public class ListBindingFactory implements BindingFactory {

  private final BindingSource bindingSource;

  public ListBindingFactory(BindingSource source) {
    this.bindingSource = source;
  }

  public Binding newBinding(String description, ComponentResources container, ComponentResources component,
                            String expression, Location location) {
    List<Binding> delegates = new ArrayList<Binding>();
    String[] items = expression.split(",");
    boolean invariant = true;

    for (String item : items) {
      Binding binding = bindingSource.newBinding(description, container, component,
                                                 BindingConstants.PROP, item, location);
      invariant = invariant && binding.isInvariant();
      delegates.add(binding);
    }

    return new ListBinding(delegates, invariant);
  }
}
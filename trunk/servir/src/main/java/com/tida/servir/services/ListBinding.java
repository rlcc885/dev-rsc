package com.tida.servir.services;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.Binding;
import org.apache.tapestry5.internal.bindings.AbstractBinding;

public class ListBinding extends AbstractBinding {

  private final List<Binding> delegates;
  private final boolean invariant;

  public ListBinding(List<Binding> delegates, boolean invariant) {
    this.delegates = delegates;
    this.invariant = invariant;
  }

  public Object get() {
    List<Object> values = new ArrayList<Object>(delegates.size());

    for (Binding binding : delegates) {
      values.add(binding.get());
    }

    return values;
  }

  public boolean isInvariant() {
    return invariant;
  }

  public Class<List> getBindingType() {
    return List.class;
  }
}
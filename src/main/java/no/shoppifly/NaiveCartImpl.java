package no.shoppifly;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
class NaiveCartImpl implements CartService, ApplicationListener<ApplicationReadyEvent> {

    private final Map<String, Cart> shoppingCarts = new HashMap<>();
    private MeterRegistry meterRegistry;

    @Autowired
    public NaiveCartImpl(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public Cart getCart(String id) {
        return shoppingCarts.get(id);
    }

    @Override
    public Cart update(Cart cart) {
        if (cart.getId() == null) {
            cart.setId(UUID.randomUUID().toString());
        }
        shoppingCarts.put(cart.getId(), cart);
        return shoppingCarts.put(cart.getId(), cart);
    }

    @Override
    public String checkout(Cart cart) {
        shoppingCarts.remove(cart.getId());
        meterRegistry.counter("checkouts").increment();
        return UUID.randomUUID().toString();
    }

    @Override
    public List<String> getAllCarts() {
        return new ArrayList<>(shoppingCarts.keySet());
    }

    // @author Jim; I'm so proud of this one, took me one week to figure out !!!
    public float total() {
        return shoppingCarts.values().stream()
                .flatMap(c -> c.getItems().stream()
                        .map(i -> i.getUnitPrice() * i.getQty()))
                .reduce(0f, Float::sum);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        // Verdi av total
        Gauge.builder("carts", shoppingCarts,
                b -> b.values().size()).register(meterRegistry);

        // Denne rapporterer hvor mye penger som totalt finnes i alle shopping carts til sammen
        Gauge.builder("cartsvalue", this::total)
                .register(meterRegistry);
    }
}
package com.ngocduc.projectspringboot.model.entity;

import org.springframework.context.ApplicationEvent;

public class OrderEvent extends ApplicationEvent {

    private Orders order;

    public OrderEvent(Object source, Orders order) {
        super(source);
        this.order = order;
    }

    public Orders getOrder() {
        return order;
    }
}


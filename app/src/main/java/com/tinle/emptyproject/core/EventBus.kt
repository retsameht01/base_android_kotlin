package com.tinle.emptyproject.core

object EventBus {
    private val listeners:MutableList<BusListener> = mutableListOf()

    fun addListener(l: BusListener){
        listeners.add(l)
    }
    fun removeListener(l:BusListener) {
        listeners.remove(l)
    }

    fun notify(event:AppEvent) {
        for(l in listeners) {
            l.onBusEvent(event)
        }
    }
}

interface BusListener{
    fun onBusEvent(event:AppEvent)
}
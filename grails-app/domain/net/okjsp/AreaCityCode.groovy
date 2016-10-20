package net.okjsp

class AreaCityCode {
    String id
    String name

    static mapping = {
        id generator: 'assigned', type: 'string'
    }
}
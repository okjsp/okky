package net.okjsp

class AreaDistrictCode {
    AreaCityCode areaCityCode
    String id
    String name

    static mapping = {
        id generator: 'assigned', type: 'string'
    }
}
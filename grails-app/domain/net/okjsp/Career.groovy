package net.okjsp

class Career {

    Company company

    static belongsTo = [resume: Resume]

    static constraints = {
    }
}

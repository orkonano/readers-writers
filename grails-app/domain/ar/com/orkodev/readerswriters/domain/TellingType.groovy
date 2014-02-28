package ar.com.orkodev.readerswriters.domain

class TellingType {

    String name

    static constraints = {
        name blank: false, unique: true
    }
}

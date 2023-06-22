package de.larmic.kotlindsl.example

data class Address(val street: String, val number: String, val city: String)
data class Person(val name: String, val email: String, val age: Int, val address: Address)

// is needed to prevent access to the properties of another builder in one builder
// example:
//    person {
//        ...
//        address {
//            ...
//            email = "demo@ding.de <- throws compile error with MyDsl annotation
//        }
//    }
@DslMarker
annotation class MyDsl

@MyDsl
class AddressBuilder(
    var street: String = "Musterstraße",
    var number: String = "1a",
    var city: String = "Oldenburg",
) {
    fun build() = Address(street, number, city)
}

@MyDsl
class PersonBuilder(
    var name: String = "Max Mustermann",
    var email: String = "max@mustermann.de",
    var age: Int = 39,
    var addressBuilder: AddressBuilder = AddressBuilder(),
) {
    fun build() = Person(name, email, age, addressBuilder.build())
}

// lambda with receiver
fun person(block: PersonBuilder.() -> Unit) = PersonBuilder().apply(block).build()

// extend PersonBuilder with address lambda with receiver
fun PersonBuilder.address(block: AddressBuilder.() -> Unit) {
    addressBuilder = AddressBuilder().apply(block)
}

// old way to create test data
fun buildPerson(): Person {
    val a = AddressBuilder()
    a.city = "Entenhausen"
    a.street = "Blumenstraße"
    a.number = "13"

    val p = PersonBuilder()
    p.name = "Tick Duck"
    p.email = "tick@duck.de"
    p.age = 14
    p.addressBuilder = a

    return p.build()
}

fun main() {
    // old way
    println(buildPerson())

    // new way
    println(person {
        name = "Tick Duck"
        email = "tick@duck.de"
        age = 14
        address {
            city = "Entenhausen"
            street = "Blumenstraße"
            number = "13"
        }
    })
}
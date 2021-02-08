package parking

fun main() {
    while (true) {
        val userCommand = readLine()!!.split(" ")
        val command = ParkingLot.commandProcessing(userCommand)
        if (command == ParkingLot.Commands.EXIT) {
            return
        }
    }
}

class Car(val registrationNumber: String = "", val color: String = "")

object ParkingLot {

    // Изначально паркова без мест
    var places: Array<Car?> = emptyArray()

    fun create(qtyPlaces: Int) {
        places = Array(qtyPlaces) { i -> null }
        println("Created a parking lot with $qtyPlaces spots.")
    }

    enum class Commands {
        CREATE,
        PARK,
        LEAVE,
        STATUS,
        EXIT,
        NULL;

        companion object {
            fun getByName(name: String): Commands {
                for (enum in values()) {
                    if (name.toUpperCase() == enum.name) return enum
                }
                return NULL
            }
        }
    }

    fun commandProcessing(userCommand: List<String>): Commands {
        val command = Commands.getByName(userCommand[0])
        if(command == Commands.CREATE){
            val qtyPlaces = userCommand[1].toInt()
            create(qtyPlaces)
        } else {
            when (command) {
                Commands.PARK -> {
                    if (places.isEmpty()) {
                        println("Sorry, a parking lot has not been created.")
                        return Commands.PARK
                    }
                    val car = Car(userCommand[1], userCommand[2])
                    var isCarParked = false
                    for (i in places.indices) {
                        if (places[i] == null) {
                            places[i] = car
                            println("${car.color} car parked in spot ${i + 1}.")
                            isCarParked = true
                            break
                        }
                    }
                    if (!isCarParked) {
                        println("Sorry, the parking lot is full.")
                    }
                }
                Commands.LEAVE -> {
                    if (places.isEmpty()) {
                        println("Sorry, a parking lot has not been created.")
                        return Commands.LEAVE
                    }
                    val numPlace = userCommand[1].toInt()
                    if (places.size > numPlace - 1) {
                        if (places[numPlace - 1] != null) {
                            places[numPlace - 1] = null
                            println("Spot $numPlace is free.")
                        } else {
                            println("There is no car in spot $numPlace.")
                        }
                    }
                }
                Commands.STATUS -> {
                    if (places.isEmpty()) {
                        println("Sorry, a parking lot has not been created.")
                        return Commands.STATUS
                    }
                    var isEmptyParking = true
                    for (i in places.indices) {
                        if (places[i] != null) {
                            println("${i + 1} ${places[i]!!.registrationNumber} ${places[i]!!.color}")
                            isEmptyParking = false
                        }
                    }
                    if (isEmptyParking) {
                        println("Parking lot is empty.")
                    }
                }
            }
        }
        return command // EXIT
    }
}
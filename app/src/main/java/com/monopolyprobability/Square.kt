package com.monopolyprobability

class Square {

    var name: String
    var number: Int =  0
    var backgroundColor : Int
    var foregroundColor : Int
    var hits :Int = 0
    var hitsPercent : String = "0%"
        get()
        {
            return getPencentage(hits, numberOfRolls)
        }

    constructor(name: String, number: Int, backgroundColor: Int, foregroundColor:Int)
    {
        this.name = name
        this.number = number
        this.backgroundColor = backgroundColor
        this.foregroundColor = foregroundColor
    }

    companion object {
        var numberOfRolls : Int = 0
    }


    public fun increaseHits() : Unit
    {
        hits++

    }

    public fun resetHits() : Unit{
        hits = 0
    }

    private fun getPencentage(hits: Int, number: Int): String {
        return "%.2f".format((hits.toDouble() / number) * 100) + "%"
    }

}
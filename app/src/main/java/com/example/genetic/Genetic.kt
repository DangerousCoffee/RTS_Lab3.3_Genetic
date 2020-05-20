package com.example.genetic

import java.lang.Math.abs
import java.lang.Math.pow
import kotlin.collections.ArrayList

class Genetic(val x_arr: MutableList<Double>, val y_val: Double, val pop_num: Int = 4) {
    var pops: MutableList<MutableList<Int>> = ArrayList(this.pop_num)
    init {
        var pop: MutableList<Int> = ArrayList(4);
        for (i in 0 until pop_num) {
            pop = ArrayList(4);
            for (j in 0..3) {
                pop.add((1..(y_val/2).toInt()).random())
            }
            this.pops.add(pop)
        }

    }

    fun max(vals: MutableList<Double>): MutableList<Double> {
        var first: Double = vals[0]
        var second: Double = 0.0

        for (value in vals) {
            if(value > first){
                second = first
                first = value
            } else if (value != first && value > second) {
                second = value
            }
        }

        return arrayListOf(first, second)
    }

    fun equation(pop: MutableList<Int>): Double {
        return pop[0]*this.x_arr[0] + pop[1]*this.x_arr[1] + pop[2]*this.x_arr[2] + pop[3]*this.x_arr[3]
    }

    fun crossover(parents: MutableList<MutableList<Int>>): MutableList<Int> {
        val child: MutableList<Int> = ArrayList()
        val pivot: Int = (1 until parents[0].size).random()
        for (index in 0 until pivot) {
            child.add(parents[0][index])
        }
        for (index in pivot until parents[0].size) {
            child.add(parents[1][index])
        }

        return child
    }

    fun find_index(value: Double, arr: MutableList<Double>): Int {
        var index: Int = 0

        for (i in 0 until arr.size) {
            if  (arr[i] == value)
                index = i
        }

        return index
    }

    fun gen_new_pop(parents: MutableList<MutableList<Int>>) {
        var newPops: MutableList<MutableList<Int>> = ArrayList()

        newPops.add(parents[0])
        newPops.add(parents[1])
        for (i in 2 until this.pop_num) {
            newPops.add(crossover(parents))
        }

        for (pop in newPops) {
            pop[(0 until pop.size).random()] = (0 until 100).random()
        }

        this.pops = newPops
    }

    fun fitness(): MutableList<Int> {

        var deltas: MutableList<Double> = ArrayList()

        for (pop in this.pops) {
            deltas.add(abs(equation(pop) - this.y_val))
        }

        for (index in 0 until deltas.size) {
            if (deltas[index] == 0.0) {
                return pops[index]
            }
        }

        var inverse_deltas: MutableList<Double> = ArrayList()

        for (delta in deltas) {
            inverse_deltas.add(pow(delta, -1.0))
        }

        var inverse_total_delta = 0.0

        for (inverse in inverse_deltas) {
            inverse_total_delta += inverse
        }

        var fitnesses: MutableList<Double> = ArrayList()

        for (inverse in inverse_deltas){
            fitnesses.add(inverse/inverse_total_delta)
        }

        val fittest = max(fitnesses)

        var parents: MutableList<MutableList<Int>> = ArrayList()

        for (fitness in fittest) {
            parents.add(this.pops[find_index(fitness, fitnesses)])
        }

        gen_new_pop(parents)

        return arrayListOf(0)
    }

    fun algorithm(): MutableList<Int> {
        var pop: MutableList<Int> = arrayListOf(0)

        while(pop.size == 1){
            pop = fitness()
        }

        return pop
    }
}
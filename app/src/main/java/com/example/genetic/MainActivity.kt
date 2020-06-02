package com.example.genetic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        super.setTitle("Genetic algorithm")


        calculate_button.setOnClickListener {
            val x_arr: MutableList<Double> = ArrayList()
            x_arr.add(a_val.getText().toString().toDouble())
            x_arr.add(b_val.getText().toString().toDouble())
            x_arr.add(c_val.getText().toString().toDouble())
            x_arr.add(d_val.getText().toString().toDouble())

            val genetic: Genetic = Genetic(
                x_arr,
                y_val.getText().toString().toDouble(),
                mut.getText().toString().toDouble(),
                pop_number.getText().toString().toInt()
            )

            val solution = genetic.algorithm()

            val display: String = "Solution is " +
                    solution[0] + " * a + " +
                    solution[1] + " * b + " +
                    solution[2] + " * c + " +
                    solution[3] + " * d = " +
                    genetic.y_val.toString()

            res_view.setText(display)

        }
    }


}

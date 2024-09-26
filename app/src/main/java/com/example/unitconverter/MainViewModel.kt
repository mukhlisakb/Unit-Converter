package com.example.unitconverter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.ExpressionBuilder

class MainViewModel: ViewModel() {

    private val celcius: String = "Celcius"
    private val fahrenheit: String = "Fahrenheit"

    private val _input = MutableLiveData("0")
    val input: MutableLiveData<String> = _input

    private val _activeInputUnit = MutableLiveData(celcius)
    val activeInputUnit: LiveData<String> = _activeInputUnit

    private val _output = MutableLiveData("0")
    val output: MutableLiveData<String> = _output

    fun setInput(value: String) {
        if(_input.value == "0") {
            _input.value = value
        } else {
            _input.value += value
        }
    }

    fun clearInput() {
        _input.value = if (_input.value?.length == 1) {
            "0"
        } else {
            _input.value?.dropLast(1)
        }
    }

    fun swicthInputUnit() {
        val activeInputUnit = _activeInputUnit.value
        _activeInputUnit.value = if (activeInputUnit == celcius) {
            fahrenheit
        } else {
            celcius
        }
    }

    fun resetInputAndOutput() {
        _input.value = "0"
        _output.value = "0"
    }

    fun convertUnit() {
        val expression = _input.value
        val result = try {
            ExpressionBuilder(expression).build().evaluate()
        } catch (e: Exception) {
            return
        }

        val activeInputUnit = _activeInputUnit.value

        result.let {
            _output.value = when (activeInputUnit) {
                celcius -> convertCelciusToFahrenheit(it).toString()
                fahrenheit -> convertCelciusToFahrenheit(it).toString()
                else -> "0"
            }
        }
    }

    private fun convertCelciusToFahrenheit(celcius: Double): Double {
        return (celcius * 9 / 5) + 32
    }

    private fun convertFahrenheitToCelcius(fahrenheit: Double): Double {
        return (fahrenheit - 32) * 5 / 9
    }
}
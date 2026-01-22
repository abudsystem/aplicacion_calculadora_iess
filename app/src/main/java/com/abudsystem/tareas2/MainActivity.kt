package com.abudsystem.tareas2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.abudsystem.tareas2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnCalcularSalario.setOnClickListener {
            calcularTotal()
        }
    }

    private fun calcularSubtotal(): Double {
        val nombre = binding.etNombreEmpleado.text.toString()
        val horasText = binding.etHorasTrabajadasEnElMes.text.toString()
        val valorText = binding.etValorPorHora.text.toString()

        if (nombre.isEmpty()) {
            binding.etNombreEmpleado.error = "Campo obligatorio"
            return 0.0
        }

        if (horasText.isEmpty()) {
            binding.etHorasTrabajadasEnElMes.error = "Campo obligatorio"
            return 0.0
        }

        if (valorText.isEmpty()) {
            binding.etValorPorHora.error = "Campo obligatorio"
            return 0.0
        }

        val horas = horasText.toDouble()
        val valorHora = valorText.toDouble()

        return horas * valorHora
    }

    private fun calcularDescuentoIess(subtotal: Double): Double {
        return subtotal * 0.0945
    }

    private fun calcularTotal() {
        val subtotal = calcularSubtotal()
        if (subtotal == 0.0) return

        val descuentoIess = calcularDescuentoIess(subtotal)
        val totalARecibir = subtotal - descuentoIess

        binding.tvResultadoAqui.text =
            "El pago para ${binding.etNombreEmpleado.text} es de $${"%.2f".format(totalARecibir)}"
    }
}

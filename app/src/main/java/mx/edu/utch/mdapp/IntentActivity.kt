package mx.edu.utch.mdapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import mx.edu.utch.mdapp.R

class IntentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent)

        // Recuperar los valores del Intent
        var puntaje1:Int = intent.getIntExtra("puntajeJugadorUno",3)
        var puntaje2:Int = intent.getIntExtra("puntajeJugadorDos",3)

        // Asignar los valores a los TextViews
        val dato1TextView: TextView = findViewById(R.id.puntajeUno)
        val dato2TextView: TextView = findViewById(R.id.puntajeDos)
        val dato3ImageView: ImageView = findViewById(R.id.imWinner)
        var regreso=""
        // Dentro de onCreate en IntentActivity.kt
        val btnNuevaPartida: Button = findViewById(R.id.btnNuevaPartida)
        val btnSalir: Button = findViewById(R.id.btnSalir)

        btnNuevaPartida.setOnClickListener {
            regreso="NuevaPartida"
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("accionTurbo",regreso)
            startActivity(intent)
            finish()
        }
        btnSalir.setOnClickListener {
            regreso="Salir"
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("accionTurbo",regreso)
            startActivity(intent)
            finish()
        }

        dato1TextView.text = "$puntaje1"
        dato2TextView.text = "$puntaje2"
        if(puntaje1>puntaje2) {
            dato3ImageView.setImageResource(R.drawable.player1wins)
        }
        else {
            if(puntaje2>puntaje1) {
                dato3ImageView.setImageResource(R.drawable.player2wins)
            }
            else{
                dato3ImageView.setImageResource(R.drawable.draw)
            }
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        var regreso="NuevaPartida"
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("accionTurbo",regreso)
        startActivity(intent)
        finish()
    }






}
package mx.edu.utch.mdapp


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import mx.edu.utch.mdapp.IntentActivity
import mx.edu.utch.mdapp.databinding.ActivityMainBinding
import java.util.Collections

class MainActivity : AppCompatActivity() {
    private var turno:Boolean?=true
    private var puntosJugadorUno:Int?=0
    private var puntosJugadorDos:Int?=0
    private var puntosTotal:Int?=0
    private var primeraCarta:ImageView?=null
    private var primeraImagen:Int?=0
    private var clicked:Boolean?=true
    private var tiempo:Long?=2000
    private var reiniciar=0
    private var retorno=false
    private var out=false

    private var baraja=ArrayList<Int>(
        listOf(
            R.drawable.cloud,
            R.drawable.day,
            R.drawable.moon,
            R.drawable.night,
            R.drawable.rain,
            R.drawable.rainbow,
            R.drawable.cloud,
            R.drawable.day,
            R.drawable.moon,
            R.drawable.night,
            R.drawable.rain,
            R.drawable.rainbow
        )
    )
    private var imageView:ArrayList<ImageView>?=null
    private var binding:ActivityMainBinding?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding!!.root)
        imageView= ArrayList(listOf(
            binding!!.lytCartas.im11, binding!!.lytCartas.im12, binding!!.lytCartas.im13,

            binding!!.lytCartas.im21, binding!!.lytCartas.im22, binding!!.lytCartas.im23,

            binding!!.lytCartas.im31, binding!!.lytCartas.im32, binding!!.lytCartas.im33,

            binding!!.lytCartas.im41, binding!!.lytCartas.im42, binding!!.lytCartas.im43,

            )
        )

        binding!!.fabPrincipal.setOnClickListener{

            if (reiniciar==1) {
                newGame()
                reiniciar=0
            }else{
                Toast.makeText(this,"Presione de nuevo para una nueva partida", Toast.LENGTH_LONG).show()
                reiniciar = reiniciar+1
            }

        }

        setSupportActionBar(binding!!.mainBottomAppBar)

        Collections.shuffle(baraja)
        startOn()//Inicio del programa
        clickOn()//Lógica del programa
        handleIntentActions()

    }
    private fun handleIntentActions() {
        var accion: String? = intent.getStringExtra("accionTurbo")
        if (accion == "Salir") {
            out = true
            finish()
        } else if (accion == "NuevoJuego") {
            newGame()
        }
        else{newGame()}
    }

    private fun clickOn() {
        for(i in (0 ..  imageView!!.size -1)){

            imageView!![i].setOnClickListener {
                imageView!![i].setImageResource(baraja[i])
                guardaClick(imageView!![i], baraja[i])
            }
        }
    }
    private fun reiniciarCards() {
        for(i in (0 ..  imageView!!.size -1)){
            imageView!![i].setImageResource(R.drawable.reverso)
            imageView!![i].visibility = View.VISIBLE
        }
    }

    private fun guardaClick(imgv: ImageView, i: Int) {
        if(clicked!!) {//True significa que es el primer click
            primeraCarta = imgv
            primeraImagen = i
            primeraCarta!!.isEnabled = false
            clicked = !clicked!!
        }else{
            xctivar(false)//desabilita todas las cartas
            //Pausa para ver si son iguales
            var handler = Handler(Looper.getMainLooper())
            tiempo?.let {
                handler.postDelayed({
                    if(primeraImagen== i){
                        primeraCarta!!. visibility = View.INVISIBLE
                        imgv.visibility=View.INVISIBLE
                        if(turno!!){
                            puntosJugadorUno = puntosJugadorUno!!+1
                            binding?.lytMarcador?.mainActivityTvPlayerScore2?.text = "Puntos: $puntosJugadorUno"

                        }
                        else{
                            puntosJugadorDos = puntosJugadorDos!!+1
                            binding?.lytMarcador?.mainActivityTvPlayerScore1?.text = "Puntos: $puntosJugadorDos"

                        }
                        puntosTotal =puntosJugadorUno!!+puntosJugadorDos!!
                        if(puntosTotal == 6){
                            retorno=true
                            val intent = Intent(this, IntentActivity::class.java)
                            intent.putExtra("puntajeJugadorUno", puntosJugadorUno)
                            intent.putExtra("puntajeJugadorDos", puntosJugadorDos)
                            startActivity(intent)
                            finish()
                        }
                        }else{
                            //Las cartas no son iguales
                            primeraCarta!!.setImageResource(R.drawable.reverso)
                            imgv.setImageResource(R.drawable.reverso)
                            turno=!turno!!
                            primeraCarta!!.isEnabled=true
                            startOn()
                        }
                    xctivar(true)
                }, 1000)
                clicked =!clicked!!}
        }
    }

    private fun xctivar(b: Boolean) {
        for(i in (0 ..  imageView!!.size -1)){
            imageView!![i].isEnabled = b
        }
    }

    private fun startOn() {
        if (turno!!){
            binding!!.lytMarcador.mainActivityTvPlayer1.setTextColor(Color.MAGENTA)
            binding!!.lytMarcador.mainActivityTvPlayer1.visibility=View.VISIBLE
            binding!!.lytMarcador.mainActivityTvPlayer2.setTextColor(Color.GREEN)
            binding!!.lytMarcador.mainActivityTvPlayer2.visibility=View.INVISIBLE

        }
        else{
            binding!!.lytMarcador.mainActivityTvPlayer1.setTextColor(Color.MAGENTA)
            binding!!.lytMarcador.mainActivityTvPlayer1.visibility=View.INVISIBLE
            binding!!.lytMarcador.mainActivityTvPlayer2.setTextColor(Color.GREEN)
            binding!!.lytMarcador.mainActivityTvPlayer2.visibility=View.VISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.option_1 -> {
                newGame()
                true
            }
            R.id.option_2 -> {
                showContent()
                true
            }R.id.option_3 -> {
                terminar()
                retorno=true
                true
            }R.id.option_4 -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun terminar() {
        val intent = Intent(this, IntentActivity::class.java)
        intent.putExtra("puntajeJugadorUno", puntosJugadorUno)
        intent.putExtra("puntajeJugadorDos", puntosJugadorDos)
        startActivity(intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main_activity , menu)
        return true
    }
    private fun newGame() {
        Log.d("Click","opcion 1")
            turno=true
            startOn()
            puntosJugadorUno = 0
            puntosJugadorDos = 0
            puntosTotal = 0
            // Actualizar los textos de los puntajes en la interfaz de usuario
            binding?.lytMarcador?.mainActivityTvPlayerScore1?.text = "Puntos: $puntosJugadorUno"
            binding?.lytMarcador?.mainActivityTvPlayerScore2?.text = "Puntos: $puntosJugadorDos"
            reiniciarCards()
            Collections.shuffle(baraja)
            xctivar(true)
            Toast.makeText(this, "¡Nueva partida comenzada!", Toast.LENGTH_SHORT).show()
    }

    private fun showContent() {
        xctivar(false)
        // Muestra las imágenes asignadas a cada carta durante dos segundos
        for ((index, imageView) in imageView!!.withIndex()) {
            imageView.setImageResource(baraja[index]) // Muestra la imagen asignada a la carta
        }
        Handler(Looper.getMainLooper()).postDelayed({
            for (imageView in imageView!!) {
                imageView.setImageResource(R.drawable.reverso)
            }
            xctivar(true)
        }, 2000)
    }



}

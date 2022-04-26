package com.example.request

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.request.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var queue: RequestQueue
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUpdatePokemon.setOnClickListener{
            var num = binding.etPokemonAmount.text

            if(num.isEmpty()){
                Toast.makeText(this,"cantidad invalida", Toast.LENGTH_LONG).show()
            }else{
                queue = Volley.newRequestQueue(this)
                getPokemonList(Integer.parseInt(num.toString()))
            }
        }

    }

    fun getPokemonList(listAmount: Int) {
        var url = "https://pokeapi.co/api/v2/pokemon/?limit=${listAmount}"

        val jsonRequest = JsonObjectRequest(url, Response.Listener<JSONObject>{response ->
            Log.i("JSONRESPONSE", response.getJSONArray("results").toString())
        },
        Response.ErrorListener{ error ->
            Log.w("JSONRESPONSE", error.message as String)
        })

        queue.add(jsonRequest)
    }

    override fun onStop() {
        super.onStop()
        queue.cancelAll("Stopped")
    }
}
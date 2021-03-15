package com.example.tripletriadwiki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import org.json.JSONArray
import org.w3c.dom.Text
import java.io.InputStream

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.title = "Card Details"
        this.actionBar?.setDisplayHomeAsUpEnabled(true)
        val id: Int = intent.getIntExtra("id",0)
        var card: Card = getCardData(id)
        val cardPhoto: ImageView = findViewById(R.id.card_img)
        val cardName: TextView = findViewById(R.id.txt_card_name)
        val cardType: TextView = findViewById(R.id.txt_card_type)
        val leftRank: TextView = findViewById(R.id.txt_left_rank)
        val rightRank: TextView = findViewById(R.id.txt_right_rank)
        val topRank: TextView = findViewById(R.id.txt_top_rank)
        val bottomRank: TextView = findViewById(R.id.txt_bottom_rank)
        val elemental: TextView = findViewById(R.id.txt_elemental)

        cardPhoto.setBackgroundResource(card.photo)
        cardName.setText(card.name)
        cardType.setText("Level ${card.level} ${card.type} Card")
        leftRank.setText(card.attributes["left"].toString())
        rightRank.setText(card.attributes["right"].toString())
        topRank.setText(card.attributes["top"].toString())
        bottomRank.setText(card.attributes["bottom"].toString())
        elemental.setText(card.element)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getCardData(id: Int): Card{
        val inputStream: InputStream = assets.open("triple-triad-cards-data.json")
        var json = inputStream.bufferedReader().use { it.readText() }
        var card = Card()
        var jsonArr = JSONArray(json)
        for(i in 0..jsonArr.length()-1){
            var jsonObj = jsonArr.getJSONObject(i)
            if (id.equals(jsonObj.getInt("id"))){
                card.name = jsonObj.getString("name")
                card.photo = getResources().getIdentifier(jsonObj.getString("drawable"), "drawable", getPackageName());
                card.id = jsonObj.getInt("id")
                card.type = jsonObj.getString("type")
                card.level = jsonObj.getInt("level")
                card.element = jsonObj.getString("element")
                var tempArr: JSONArray = jsonObj.getJSONArray("attributes")
                card.attributes= mapOf(
                    "left" to  if(tempArr[3].toString().equals("10")){"A"}else{tempArr[3].toString()},
                    "right" to  if(tempArr[1].toString().equals("10")){"A"}else{tempArr[1].toString()},
                    "top" to  if(tempArr[0].toString().equals("10")){"A"}else{tempArr[0].toString()},
                    "bottom" to  if(tempArr[2].toString().equals("10")){"A"}else{tempArr[2].toString()}
                )
            }
        }
        return card
    }
}
package com.example.tripletriadwiki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import org.json.JSONArray
import org.w3c.dom.Text
import java.io.InputStream

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_CARD = "extra_card"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.title = "Card Details"
        this.actionBar?.setDisplayHomeAsUpEnabled(true)
        var card: Card = intent.getParcelableExtra<Card>(EXTRA_CARD) as Card
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
}
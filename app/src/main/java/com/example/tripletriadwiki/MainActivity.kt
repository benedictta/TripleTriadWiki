package com.example.tripletriadwiki

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    private var cardList: ArrayList<Card> = arrayListOf()
    private lateinit var rvCards: RecyclerView
    private var selectedFilter: Int = 0
    var selectedType: String = "Show All"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvCards = findViewById(R.id.rv_cards)
        rvCards.setHasFixedSize(true)

        showList(selectedType)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun updateItemCount(itemCount: Int){
        var txtItemCount: TextView = findViewById(R.id.txt_item_count)
        txtItemCount.setText("Showing $itemCount items")
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.action_about -> {
                val moveAbout = Intent(this@MainActivity,AboutActivity::class.java)
                startActivity(moveAbout)
            }
            R.id.action_help -> {
                showTripleTriadInfo()
            }
            R.id.action_filter -> {
                showFilterDialog()
            }
        }
    }

    private fun getCardsFromJSON(type: String?): ArrayList<Card>{
        val inputStream: InputStream = assets.open("triple-triad-cards-data.json")
        var json = inputStream.bufferedReader().use { it.readText() }

        var jsonArr = JSONArray(json)
        val list = arrayListOf<Card>()
        for(i in 0..jsonArr.length()-1){
            var jsonObj = jsonArr.getJSONObject(i)
            var card = Card()
            card.name = jsonObj.getString("name")
            //card.name = jsonArr.length().toString()
            card.photo = getResources().getIdentifier(jsonObj.getString("drawable"), "drawable", getPackageName());
            //card.photo = R.drawable.squall
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
            if (type.equals("Show All")){
                list.add(card)
            }
            else{
                if(card.type.equals(type)){
                    list.add(card)
                }
            }
        }
        return list
    }

    private fun showFilterDialog(){
        val type = arrayOf("Show All","Monster", "Boss", "Guardian Force", "Player")
        val builder = AlertDialog.Builder(this)
        with(builder){
            setTitle(getString(R.string.filter_dialog_title))
            setSingleChoiceItems(type, selectedFilter,
                DialogInterface.OnClickListener { dialog, which ->
                    selectedFilter = which
                    selectedType = type[which]
                })
            setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                showList(selectedType)
            })
            show()
        }
    }

    private fun showTripleTriadInfo(){
        val builder = AlertDialog.Builder(this)
        with(builder){
            setIcon(R.drawable.ic_details)
            setTitle(getString(R.string.about_triple_triad))
            setMessage("Triple Triad a card game originating from Final Fantasy VIII. In the game two players face off against one another, one side playing as \"blue\", the other as \"red\" on a 3x3 grid. Each player has five cards in a hand and the aim is to capture the opponent's cards by turning them into the player's own color of red or blue.")
            setPositiveButton("OK",null)
            show()
        }
    }

    private fun showList(filter: String?){
        cardList = getCardsFromJSON(filter)
        showRecyclerList()
    }

    private fun showRecyclerList() {
        rvCards.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListCardAdapter(cardList)
        rvCards.adapter = listHeroAdapter
        updateItemCount(cardList.size)

        listHeroAdapter.setOnItemClickCallback(object : ListCardAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Card) {
                showSelectedCard(data)
            }
        })
    }

    private fun showSelectedCard(card: Card) {
        val moveDetail = Intent(this@MainActivity,DetailActivity::class.java)
        moveDetail.putExtra(DetailActivity.EXTRA_CARD,card)
        startActivity(moveDetail)
    }
}
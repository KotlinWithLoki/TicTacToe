package android.atest.tictactoe

import android.annotation.SuppressLint
import android.atest.tictactoe.databinding.ActivityMainBinding
import android.graphics.Color
import android.icu.text.CaseMap.Title
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    // By:LOKI

    enum class Turn
    {
        NOUGHT,
        CROSS
    }

    // By:LOKI


    private var firstTurn = Turn.CROSS
    private var currentTurn = Turn.CROSS
    // By:LOKI

    private var crossesScore = 0
    private var noughtsScore = 0

    private var boardList = mutableListOf<Button>()

    lateinit var binding: ActivityMainBinding
    // By:LOKI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBoard()

    }

    private fun initBoard() {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    private fun fullBoard(): Boolean {

        for (button in boardList)
        {
            if (button.text == "")
                return false
        }
        return true

    }
    // By:LOKI

    fun boardTapped(view: View){
        if (view !is Button)
            return
        addToBoard(view)

        if(chechForVictory(NOUGHT))
        {
            noughtsScore++
            result("Blue Team Win!")
        }

        if (chechForVictory(CROSS))
        {
            crossesScore++
            result("Red Team Win!")
        }

        if (fullBoard() && !chechForVictory(NOUGHT) && !chechForVictory(CROSS))
        {
            result("Draw!")
        }

    }

    private fun chechForVictory(s:String): Boolean {
        //
        if (match(binding.a1,s) && match(binding.a2,s) && match(binding.a3,s))
            return true
        if (match(binding.b1,s) && match(binding.b2,s) && match(binding.b3,s))
            return true
        if (match(binding.c1,s) && match(binding.c2,s) && match(binding.c3,s))
            return true
//
        if (match(binding.a1,s) && match(binding.b1,s) && match(binding.c1,s))
            return true
        if (match(binding.a2,s) && match(binding.b2,s) && match(binding.c2,s))
            return true
        if (match(binding.a3,s) && match(binding.b3,s) && match(binding.c3,s))
            return true
       //
        if (match(binding.a1,s) && match(binding.b2,s) && match(binding.c3,s))
            return true
        if (match(binding.a3,s) && match(binding.b2,s) && match(binding.c1,s))
            return true



        return false
    }

    private fun match(button: Button, symbol : String): Boolean = button.text == symbol
    // By:LOKI

    private fun result(title: String) {

        val message = "\nBlue Team: $noughtsScore\n\nRed Team: $crossesScore"
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Reset")
            {_,_ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()

    }

    private fun resetBoard() {
        for (button in boardList){
            button.text = ""
        }

        if (firstTurn == Turn.NOUGHT)
            firstTurn = Turn.CROSS
        else if (firstTurn == Turn.CROSS)
            firstTurn = Turn.NOUGHT

        currentTurn = firstTurn
        setTurnLabel()
    }
    // By:LOKI

    @SuppressLint("ResourceAsColor")
    private fun addToBoard(button: Button) {
        if (button.text != "")
            return

        if (currentTurn == Turn.NOUGHT)
        {
            button.text = NOUGHT
            button.setTextColor(Color.BLUE)
            currentTurn = Turn.CROSS
        }
        else if (currentTurn == Turn.CROSS)
        {
            button.text = CROSS
            button.setTextColor(Color.RED)
            currentTurn = Turn.NOUGHT
        }
        setTurnLabel()
    }

    @SuppressLint("ResourceAsColor")
    private fun setTurnLabel() {
        var turnText = ""
        if (currentTurn == Turn.CROSS) {
            turnText = "Turn: $CROSS"
            binding.turnTV.setTextColor(Color.RED)
            binding.turnTV.text = turnText
        }


        if (currentTurn == Turn.NOUGHT) {
            turnText = "Turn: $NOUGHT"
            binding.turnTV.setTextColor(Color.BLUE)
            binding.turnTV.text = turnText
        }

    }
    // By:LOKI

    companion object{
        const val NOUGHT = "0"
        const val CROSS = "X"
    }
}
package com.rsschool.quiz

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.MainActivity.Companion.userAnswerList
import com.rsschool.quiz.databinding.FragmentShareBinding
import java.lang.StringBuilder

interface RestoreInterface {
    fun goToFirstPage()
}

class FragmentShare : Fragment() {
    private var restoreInterface: RestoreInterface? = null

    private var _binding: FragmentShareBinding? = null
    private val binding get() = _binding

    private var page: Int? = null

    private var totalPoints = ""
    private var totalText = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShareBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        page = arguments?.getInt(PAGE_NUMBER)
        setShareImgClickListener()
        setBackImgClickListener()
        setExitImgClickListener()
        defineResultPoints()
    }

    private fun setShareImgClickListener() {
        binding?.imgShare?.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TITLE, "Space Quiz results")
                putExtra(Intent.EXTRA_TEXT, prepareTextToSend())
                type = "text/plain"
            }
            startActivity(sendIntent)
        }
    }

    private fun prepareTextToSend(): String {
        val str = StringBuilder()
        val res = "Space Quiz results\n\n$totalText $totalPoints"

        DataSource().infoList.forEachIndexed { index, triple ->
            val i = "\n\n${triple.first}) ${triple.second}\nYour answer: ${triple.third[userAnswerList[index]-1]}"
            str.append(i)
        }

        return res+str
    }

    private fun setBackImgClickListener() {
        binding?.imgBack?.setOnClickListener {
            userAnswerList = MutableList(5) { 0 }
            restoreInterface?.goToFirstPage()
        }
    }

    private fun setExitImgClickListener() {
        binding?.imgClose?.setOnClickListener {
            activity?.finishAndRemoveTask()
        }
    }

    private fun defineResultPoints() {
        var count = 0
        userAnswerList.forEachIndexed { index, pair ->
            if (pair == DataSource().trueAnswerList[index]) {
                count++
            }
        }
        showResultText(count)
    }

    private fun showResultText(point: Int) {
        var plusString = ""
        when (point) {
            0 -> {
                totalPoints = "0%"
                totalText = "Space Quiz не пройден!"
                plusString = "$totalText\n$totalPoints"
            }
            1 -> {
                totalPoints = "20%"
                totalText = "Погугли и попробуй ещё раз!"
                plusString = "$totalText\n$totalPoints"
            }
            2 -> {
                totalPoints = "40%"
                totalText = "Ты можешь лучше!"
                plusString = "$totalText\n$totalPoints"
            }
            3 -> {
                totalPoints = "60%"
                totalText = "Неплохо!"
                plusString = "$totalText\n$totalPoints"
            }
            4 -> {
                totalPoints = "80%"
                totalText = "Почти отлично!"
                plusString = "$totalText\n$totalPoints"
            }
            5 -> {
                totalPoints = "100%"
                totalText = "Да ты просто космос!"
                plusString = "$totalText\n$totalPoints"
            }
        }
        binding?.question?.text = plusString
    }

    fun setShareInterface(inter: RestoreInterface) {
        this.restoreInterface = inter
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): FragmentShare {
            return FragmentShare()
        }

        private const val PAGE_NUMBER = "PREVIOUS_RESULT"
    }
}

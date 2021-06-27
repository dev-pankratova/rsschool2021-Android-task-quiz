package com.rsschool.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rsschool.quiz.MainActivity.Companion.userAnswerList
import com.rsschool.quiz.databinding.FragmentQuizBinding

interface NavigationInterface {
    fun setPageNumber(page: Int)
}

class FragmentQuiz : Fragment() {
    private var pagesInterface: NavigationInterface? = null
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding
    private var page: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = arguments?.getInt(PAGE_NUMBER)
        setTheme()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkIfWasSelectAnswer()
        setToolbarAction()
        setQuestionData()
        setBtnsAvailability()
        setNextBtnClickListener()
        setPreviousBtnClickListener()
    }

    private fun setTheme() {
        when (page) {
            1 -> activity?.setTheme(R.style.Theme_Quiz_First)
            2 -> activity?.setTheme(R.style.Theme_Quiz_Second)
            3 -> activity?.setTheme(R.style.Theme_Quiz_Third)
            4 -> activity?.setTheme(R.style.Theme_Quiz_Fourth)
            5 -> activity?.setTheme(R.style.Theme_Quiz_Fifth)
        }
    }

    private fun checkIfWasSelectAnswer() {
        if (page != null)  {
            when (userAnswerList[page!! - 1]) {
                1 -> binding?.optionOne?.isChecked = true
                2 -> binding?.optionTwo?.isChecked = true
                3 -> binding?.optionThree?.isChecked = true
                4 -> binding?.optionFour?.isChecked = true
                5 -> binding?.optionFive?.isChecked = true
            }
        }
    }

    private fun setToolbarAction() {
        binding?.toolbar?.apply {
            (activity as AppCompatActivity).setSupportActionBar(this)
            setNavigationOnClickListener {
                backAction()
            }
            if (page == 1) navigationIcon = null
        }
    }

    private fun setQuestionData() {
        val currentQuestion = DataSource().infoList.find { it.first == page }
        currentQuestion?.apply {
            binding?.apply {
                toolbar.title = toolbar.title.toString() + " " + first

                question.text = second

                optionOne.text = third[0]
                optionTwo.text = third[1]
                optionThree.text = third[2]
                optionFour.text = third[3]
                optionFive.text = third[4]
            }
        }
    }

    private fun setBtnsAvailability() {
        if (page == 1) binding?.previousButton?.isEnabled = false

        if (page == 5) binding?.nextButton?.text = "SUBMIT"

        if (binding?.radioGroup?.checkedRadioButtonId != -1) binding?.nextButton?.isEnabled = true

        binding?.radioGroup?.setOnCheckedChangeListener { group, checkedId ->
            binding?.nextButton?.isEnabled = true
            when (checkedId) {
                R.id.option_one -> saveUserAnswer(1)
                R.id.option_two -> saveUserAnswer(2)
                R.id.option_three -> saveUserAnswer(3)
                R.id.option_four -> saveUserAnswer(4)
                R.id.option_five -> saveUserAnswer(5)
            }
        }

    }

    private fun saveUserAnswer(answerInt: Int) {
        if (page != null) {
            userAnswerList[page!!-1] = answerInt
        }
    }

    private fun setNextBtnClickListener() {
        binding?.nextButton?.setOnClickListener {
            if (page != null) {
                if (page in 1..5) {
                    page = page!! + 1
                    pagesInterface?.setPageNumber(page!!)
                }
            }
        }
    }

    private fun setPreviousBtnClickListener() {
        binding?.previousButton?.setOnClickListener {
            backAction()
        }
    }

    private fun backAction() {
        if (page != null) {
            if (page in 2..5) {
                page = page!! - 1
                pagesInterface?.setPageNumber(page!!)
            }
        }
    }

    fun setInterface(inter: NavigationInterface) {
        this.pagesInterface = inter
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {

        fun newInstance(pageNumber: Int): FragmentQuiz {
            val fragment = FragmentQuiz()
            val args = Bundle()
            args.putInt(PAGE_NUMBER, pageNumber)
            fragment.arguments = args
            return fragment
        }

        private const val PAGE_NUMBER = "PREVIOUS_RESULT"
    }
}

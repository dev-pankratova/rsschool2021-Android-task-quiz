package com.rsschool.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding

interface NavigationInterface {
    fun setPageNumber(page: Int)
}

class FragmentQuiz : Fragment() {
    private var pagesInterface: NavigationInterface? = null
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding
    private var page: Int? = null

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
        page = arguments?.getInt(PAGE_NUMBER)
        setQuestionData()
        setBtnsAvailability()
        setNextBtnClickListener()
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
        binding?.radioGroup?.setOnCheckedChangeListener { group, checkedId ->
            binding?.nextButton?.isEnabled = true
        }
    }

    private fun setNextBtnClickListener() {
        binding?.nextButton?.setOnClickListener {
            if (page != null) {
                page = page!! + 1
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

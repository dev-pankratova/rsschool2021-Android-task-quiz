package com.rsschool.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.databinding.FragmentShareBinding

interface NavigationInterface2 {
    fun setPageNumber(page: Int)
}

class FragmentShare : Fragment() {
    private var pagesInterface: NavigationInterface? = null

    private var _binding: FragmentShareBinding? = null
    private val binding get() = _binding

    private var page: Int? = null

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
    }


    fun setInterface(inter: NavigationInterface) {
        this.pagesInterface = inter
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {

        fun newInstance(/*pageNumber: Int*/): FragmentQuiz {
            val fragment = FragmentQuiz()
            //val args = Bundle()
            //args.putInt(PAGE_NUMBER, pageNumber)
            //fragment.arguments = args
            return fragment
        }

        private const val PAGE_NUMBER = "PREVIOUS_RESULT"
    }
}

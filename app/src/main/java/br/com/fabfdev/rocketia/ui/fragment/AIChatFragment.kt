package br.com.fabfdev.rocketia.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.fabfdev.rocketia.R
import br.com.fabfdev.rocketia.databinding.FragmentAiChatBinding
import br.com.fabfdev.rocketia.ui.event.AIChatEvent
import br.com.fabfdev.rocketia.ui.viewmodel.AIChatViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AIChatFragment : Fragment() {

    private var _binding: FragmentAiChatBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AIChatViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAiChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        with(binding) {
            tietAIQuestion.doOnTextChanged { _, _, _, _ ->
                if (tilAIQuestion.error != null) {
                    tietAIQuestion.error = null
                }
            }
            btnSendAIQuestion.setOnClickListener {
                val questionText = tietAIQuestion.text.toString()
                if (questionText.isNotEmpty()) {
                    viewModel.onEvent(AIChatEvent.SendUserQuestionToAI(questionText))
                } else {
                    tietAIQuestion.error = getString(R.string.campo_obrigatorio)
                }
            }
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.selectedStack.collect { selectedStack ->
                        selectedStack?.let {
                            binding.tvHelloWhichStackAreYouGoingToStudy.text =
                                getString(R.string.ola_dev, selectedStack)
                            binding.tilAIQuestion.hint =
                                getString(R.string.qual_a_sua_duvida_sobre, selectedStack)
                        }
                    }
                }
                launch {
                    viewModel.aiChatBySelectedStack.collect { aiChatBySelectedStack ->
                        println("Size is: ${aiChatBySelectedStack.size}")
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
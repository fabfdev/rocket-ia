package br.com.fabfdev.feature.aichat.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.fabfdev.core.ui.R
import br.com.fabfdev.feature.aichat.adapter.AIChatAdapter
import br.com.fabfdev.feature.aichat.databinding.FragmentAiChatHistoryBinding
import br.com.fabfdev.feature.aichat.event.AIChatHistoryEvent
import br.com.fabfdev.feature.aichat.viewmodel.AIChatHistoryViewModel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AIChatHistoryFragment : Fragment() {

    private var _binding: FragmentAiChatHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AIChatHistoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAiChatHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        with(binding) {
            setupStackChips()

            rvHistoryAIChat.adapter = AIChatAdapter()
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.selectedStack.collect { selectedStack ->
                        selectedStack?.let {
                            binding.getStackChipId(selectedStack)?.let { selectedStackChipId ->
                                viewModel.onEvent(
                                    AIChatHistoryEvent.SelectedStack(
                                        selectedStack,
                                        selectedStackChipId
                                    )
                                )
                            }
                        }
                    }
                }

                launch {
                    viewModel.selectedStackChipId.collect { selectedStackChipId ->
                        selectedStackChipId?.let { binding.changeSelectedStack(selectedStackChipId) }
                    }
                }

                launch {
                    viewModel.aiChatHistoryBySelectedStack.collect { aiChatBySelectedStack ->
                        val aiChatAdapter = binding.rvHistoryAIChat.adapter as? AIChatAdapter
                        aiChatAdapter?.submitList(aiChatBySelectedStack) {
                            binding.rvHistoryAIChat.smoothScrollToPosition(0)
                        }
                    }
                }
            }
        }
    }

    private fun FragmentAiChatHistoryBinding.setupStackChips() {
        flwFilterStackOptions.referencedIds.forEach { stackChipId ->
            val stackChip = root.findViewById<Chip>(stackChipId)

            stackChip.setOnClickListener {
                viewModel.onEvent(
                    AIChatHistoryEvent.SelectedStack(
                        stackChip.text.toString(),
                        stackChipId
                    )
                )
            }
        }
    }

    private fun FragmentAiChatHistoryBinding.getStackChipId(stackName: String): Int? {
        return flwFilterStackOptions.referencedIds.find { stackChipId ->
            val stackChip = root.findViewById<Chip>(stackChipId)
            stackChip.text == stackName
        }
    }

    private fun FragmentAiChatHistoryBinding.changeSelectedStack(selectedStackChipId: Int) {
        flwFilterStackOptions.referencedIds.forEach { stackChipId ->
            val stackChip = root.findViewById<Chip>(stackChipId)

            stackChip?.apply {
                setChipStrokeColorResource(
                    if (stackChip.id == selectedStackChipId) {
                        R.color.white
                    } else {
                        R.color.border_default
                    }
                )
                isChecked = stackChip.id == selectedStackChipId
            }
        }

        root.findViewById<Chip>(selectedStackChipId)?.let { selectedChip ->
            hsvFilterStackOptions.doOnLayout {
                val targetScrollX = selectedChip.left - (hsvFilterStackOptions.width - selectedChip.width) / 2
                hsvFilterStackOptions.smoothScrollTo(targetScrollX.coerceAtLeast(0), 0)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
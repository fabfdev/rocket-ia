package br.com.fabfdev.rocketia.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import br.com.fabfdev.rocketia.R
import br.com.fabfdev.rocketia.databinding.FragmentChooseStackBinding
import br.com.fabfdev.rocketia.ui.event.ChooseStackUiEvent
import br.com.fabfdev.rocketia.ui.viewmodel.ChooseStackViewModel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooseStackFragment : Fragment() {

    private var _binding: FragmentChooseStackBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChooseStackViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseStackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        with(binding) {
            setupStackChips()
            btnChooseStackConfirm.setOnClickListener {
                findNavController().navigate(R.id.action_chooseStackFragment_to_homeFragment)
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.selectedStackChipId.collect { selectedStackChipId ->
                        selectedStackChipId?.let { binding.changeSelectedStack(selectedStackChipId) }
                    }
                }

                launch {
                    viewModel.isConfirmedNewStack.collect { isConfirmedNewStack ->
                        binding.btnChooseStackConfirm.isEnabled = isConfirmedNewStack
                    }
                }
            }
        }
    }

    private fun FragmentChooseStackBinding.setupStackChips() {
        flwChooseStackOptions.referencedIds.forEach { stackChipId ->
            val stackChip = root.findViewById<Chip>(stackChipId)

            stackChip.setOnClickListener {
                viewModel.onEvent(
                    ChooseStackUiEvent.SelectedStack(
                        stackChip.text.toString(),
                        stackChipId
                    )
                )
            }
        }
    }

    private fun FragmentChooseStackBinding.changeSelectedStack(selectedStackChipId: Int) {
        flwChooseStackOptions.referencedIds.forEach { stackChipId ->
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
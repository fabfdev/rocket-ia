package br.com.fabfdev.feature.onboarding.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import br.com.fabfdev.core.navigation.DeepLinks
import br.com.fabfdev.feature.onboarding.event.WelcomeUiEvent
import br.com.fabfdev.feature.onboarding.viewmodel.WelcomeViewModel
import br.com.fabfdev.feature.onboarding.databinding.FragmentWelcomeBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

//@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding get() = _binding!!

    private val viewModel: WelcomeViewModel by viewModel()
//    private val viewModel: WelcomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onEvent(WelcomeUiEvent.CheckHasSelectedStack)

        setupObservers()

        with(binding) {
            btnWelcomeStart.setOnClickListener {
                findNavController().navigate(DeepLinks.CHOOSE_STACK.toUri())
            }
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    uiState.hasSelectedStack?.let { hasSelectedStack ->
                        if (hasSelectedStack) {
                            findNavController().navigate(DeepLinks.HOME.toUri())
                        } else {
                            binding.pbProgressBar.visibility = View.GONE
                            binding.llWelcomeContainer.visibility = View.VISIBLE
                        }
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
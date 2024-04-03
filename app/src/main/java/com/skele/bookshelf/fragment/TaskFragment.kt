package com.skele.bookshelf.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skele.bookshelf.MainActivity
import com.skele.bookshelf.R
import com.skele.bookshelf.databinding.FragmentTaskBinding

private const val ARG_PARAM1 = "param1"

/**
 * A fragment represents a reusable portion of your app's UI.
 * A fragment defines and manages its own layout, has its own lifecycle, and can handle its own input events.
 * Fragments can't live on their own. They must be hosted by an activity or another fragment.
 * The fragment’s view hierarchy becomes part of, or attaches to, the host’s view hierarchy.
 *
 * Fragments introduce modularity and reusability into activity’s UI by dividing the UI into discrete chunks.
 * While activity is in the STARTED lifecycle state or higher, fragments can be added, replaced, or removed.
 * And keeping record of these changes in a back stack that is managed by the activity, so that the changes can be reversed.
 *
 * [link : fragments doc](https://developer.android.com/guide/fragments)
 */
class TaskFragment private constructor() : Fragment() {

    private var param1: String? = null

    private var _binding : FragmentTaskBinding? = null
    private val binding get() = _binding!!

    // Reference to the holder activity
    // Because fragment cannot exist without an activity, it's guaranteed to access the host activity.
    lateinit var activity: MainActivity

    // Called when fragment is attached to the activity or fragment
    // Pair with 'onDetach()'
    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity = context as MainActivity
    }

    // Provided arguments are taken out and assigned as field.
    // Since the view is not created yet, cannot perform UI initializations here.
    // It can be done in `onViewCreated`.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get field variables from the argument
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    // Inflates and create view
    // Pair with `onDestroyView`
    // It's safer to initialize UI on `onViewCreated` since the view is in creation process.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Completed creating the view
    // UI initializations and adding listeners are done here.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    // Visible to the user.
    override fun onStart() {
        super.onStart()
    }

    // Fragment is visible and has focus.
    override fun onResume() {
        super.onResume()
    }

    // User no longer has interaction with the fragment.
    // Save state to be persisted.
    override fun onPause() {
        super.onPause()
    }

    // No longer visible to the user
    override fun onStop() {
        super.onStop()
    }

    // Fragment and view has different life cycle.
    // Fragment can be alive even if the view is destroyed.
    override fun onDestroyView() {
        super.onDestroyView()

        // With the viewbinding, reference to the view stays alive even after the `onDestroyView()`.
        // Thus, the view must be detached from the fragment in order to avoid memory leak.
        _binding = null
    }
    // Fragment is being destroyed.
    override fun onDestroy() {
        super.onDestroy()
    }
    // Fragment is being removed from the activity.
    override fun onDetach() {
        super.onDetach()
    }

    companion object {
        /**
         * Supply arguments with setArguments and later retrieved by the Fragment with getArguments.
         * These arguments are automatically saved and restored alongside the Fragment.
         * Later, restore fragment using bundle provided in the arguments.
         */
        @JvmStatic
        fun newInstance(param1: String) =
            TaskFragment().apply {
                // send parameters to fragment through arguments
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}
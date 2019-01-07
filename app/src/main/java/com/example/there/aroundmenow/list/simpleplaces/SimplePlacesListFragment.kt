package com.example.there.aroundmenow.list.simpleplaces

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.DataListFragment
import com.example.there.aroundmenow.base.architecture.view.ViewObservingFragment
import com.example.there.aroundmenow.model.UISimplePlace
import com.example.there.aroundmenow.util.event.TaggedEvent
import com.example.there.aroundmenow.util.ext.hide
import com.example.there.aroundmenow.util.ext.show
import kotlinx.android.synthetic.main.fragment_simple_place_list.*
import org.greenrobot.eventbus.EventBus


class SimplePlacesListFragment : ViewObservingFragment(), DataListFragment<List<UISimplePlace>> {

    private val placesAdapter: SimplePlacesAdapter by lazy { SimplePlacesAdapter() }

    private lateinit var eventTag: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_EVENT_TAG)) eventTag = it.getString(ARG_EVENT_TAG)!!
        }
    }

    override fun onInflate(context: Context?, attrs: AttributeSet?, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)

        val styledAttrs = context?.obtainStyledAttributes(attrs, R.styleable.SimplePlacesFragment)
        styledAttrs?.getText(R.styleable.SimplePlacesFragment_eventTag)?.let { eventTag = it.toString() }
        styledAttrs?.recycle()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_simple_place_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(simple_places_recycler_view) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = placesAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                setDrawable(ContextCompat.getDrawable(context, R.drawable.pois_list_divider)!!)
            })
        }
    }

    override fun observeViews() {
        placesAdapter.placeSelected.subscribeWithAutoDispose {
            if (::eventTag.isInitialized) EventBus.getDefault().post(TaggedEvent(eventTag, it))
        }
    }

    override fun onValue(value: List<UISimplePlace>) {
        simple_places_loading_progress_bar?.hide()
        simple_places_error_text_view?.hide()
        if (value.isEmpty()) {
            no_simple_places_found_text_view?.show()
        } else {
            no_simple_places_found_text_view?.hide()
            placesAdapter.places = value
        }
    }

    override fun onError(message: String) {
        simple_places_loading_progress_bar?.hide()
        no_simple_places_found_text_view?.hide()
        simple_places_error_text_view?.show()
        simple_places_error_text_view?.text = message
    }

    override fun onLoading() {
        simple_places_loading_progress_bar?.show()
        no_simple_places_found_text_view?.hide()
        simple_places_error_text_view?.hide()
    }

    companion object {
        private const val ARG_EVENT_TAG = "ARG_EVENT_TAG"

        fun with(eventTag: String): SimplePlacesListFragment = SimplePlacesListFragment().apply {
            arguments = Bundle().apply { putString(ARG_EVENT_TAG, eventTag) }
        }
    }
}

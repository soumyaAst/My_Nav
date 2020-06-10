package com.example.mynav.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynav.R
import com.example.mynav.adapters.MsgAdapter
import com.example.mynav.objects.MsgObj
import kotlinx.android.synthetic.main.known_frag.*

class KnownFrag(var mslist:MutableList<MsgObj>): Fragment() {


      public lateinit var msgAdapter: MsgAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        msgAdapter= MsgAdapter(mslist)
        super.onViewCreated(view, savedInstanceState)
        rv_knownfrag.adapter=msgAdapter

        rv_knownfrag.layoutManager=LinearLayoutManager(view.context)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.known_frag,container,false)
    }

}

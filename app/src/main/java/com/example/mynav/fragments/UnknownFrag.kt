package com.example.mynav.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynav.R
import com.example.mynav.adapters.MsgAdapter
import com.example.mynav.objects.MsgObj
import kotlinx.android.synthetic.main.unknown_frag.*

class UnknownFrag(var mslist:MutableList<MsgObj>) :Fragment() {


        public lateinit var msgAdapter: MsgAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        msgAdapter = MsgAdapter(mslist)
        rv_unknownfrag.adapter=msgAdapter

        rv_unknownfrag.layoutManager= LinearLayoutManager(view.context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.unknown_frag,container,false)
    }

}

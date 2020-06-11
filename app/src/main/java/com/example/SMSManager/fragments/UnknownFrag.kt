package com.example.SMSManager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.SMSManager.R
import com.example.SMSManager.adapters.MsgAdapter
import com.example.SMSManager.objects.MsgObj
import kotlinx.android.synthetic.main.unknown_frag.*

class UnknownFrag(var mslist:MutableList<MsgObj>) :Fragment() {


        lateinit var msgAdapter: MsgAdapter


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

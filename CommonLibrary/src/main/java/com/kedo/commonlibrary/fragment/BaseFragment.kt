package com.kedo.commonlibrary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.kedo.commonlibrary.ext.inflateBindingWithGeneric

/**
 * @Package: com.kedo.commonlibrary.fragment
 * @ClassName: BaseFragment
 * @Author: LiuTao
 * @CreateDate: 2/16/23 3:52 PM
 */
abstract class BaseFragment<T : ViewBinding>: Fragment() {

    private var _binding: T? = null
    protected val mBinding by lazy {
        _binding!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBindingWithGeneric(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initData()
        initEvent()
    }

    fun showLoading(loadingMessage: String){

    }

    fun dismissLoading(){

    }

    abstract fun initUi()

    abstract fun initData()

    abstract fun initEvent()

}
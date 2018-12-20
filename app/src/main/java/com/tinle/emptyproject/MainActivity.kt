package com.tinle.emptyproject

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tinle.emptyproject.data.Post
import com.tinle.emptyproject.view.CheckinFragment
import com.tinle.emptyproject.vm.MainViewModel
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import com.tinle.emptyproject.services.MusicService
import com.tinle.emptyproject.view.CountDownFragment


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    @Inject
    lateinit var vmFactory:ViewModelProvider.Factory
    lateinit var vieModel:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        vieModel = ViewModelProviders.of(this, vmFactory).get(MainViewModel::class.java)
        setContentView(R.layout.activity_main)
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.fragment_container, CheckinFragment())
        trans.commit();
        //postList.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, MusicService::class.java)

        // Below code will invoke serviceConnection's onServiceConnected method.
        //bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

    }

    override fun onStop() {
        super.onStop()
    }

    override fun onBackPressed() {

    }

    inner class PostAdapter(val posts:List<Post>):RecyclerView.Adapter<PostHolder>(){
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PostHolder {
            val view = LayoutInflater.from(p0.context).inflate(R.layout.todo_row, p0, false)
            val params = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT)
            //view.layoutParams = params
            return PostHolder(view)
        }

        override fun getItemCount(): Int {
            return posts.size
        }

        override fun onBindViewHolder(holder: PostHolder, pos: Int) {
            val post = posts[pos]
            holder.userId.text = post.userId
            holder.todoId.text = post.id
            holder.title.text = post.title
            holder.body.text = post.body
        }

    }

    inner class PostHolder(view:View):RecyclerView.ViewHolder(view){
        val userId:TextView = view.findViewById(R.id.userId)
        val todoId:TextView = view.findViewById(R.id.todoId)
        val title:TextView = view.findViewById(R.id.title)
        val body:TextView = view.findViewById(R.id.body)
    }
}

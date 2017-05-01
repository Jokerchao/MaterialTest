package com.example.materialtest;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private SwipeRefreshLayout swipeRefresh;
    private Fruit[] fruits={
            new Fruit("Apple",R.drawable.apple),
            new Fruit("Banana",R.drawable.banana),
            new Fruit("Orange",R.drawable.orange),
            new Fruit("Cherry",R.drawable.cherry),
            new Fruit("Grape",R.drawable.grape),
            new Fruit("Mango",R.drawable.mango),
            new Fruit("Pineapple",R.drawable.pineapple),
            new Fruit("strawberry",R.drawable.strawberry),
            new Fruit("Pear",R.drawable.pear)
    };
    private List<Fruit> fruitList=new ArrayList<>();

    private FruitAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView= (NavigationView) findViewById(R.id.nav_view);
        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        //获取ActionBar的实例
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            //让导航按钮显示出来
            actionBar.setDisplayHomeAsUpEnabled(true);
            //设置导航按钮的图标，因为默认是返回的logo
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        //设置默认选中的菜单项
        navigationView.setCheckedItem(R.id.nav_call);
        //设置菜单项选中的监听器
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_call:
                        Toast.makeText(MainActivity.this,"You Clicked Call",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_friends:
                        Toast.makeText(MainActivity.this,"You Clicked Friends",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_location:
                        Toast.makeText(MainActivity.this,"You Clicked Location",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_mail:
                        Toast.makeText(MainActivity.this,"You Clicked Mail",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_task:
                        Toast.makeText(MainActivity.this,"You Clicked Task",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        initFruits();
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycle_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        //设置下拉进度条的颜色
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.backup:
                Toast.makeText(this,"You Clicked Backup",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"You Clicked Delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this,"You Clicked Settings",Toast.LENGTH_SHORT).show();
                break;
            //HomeAdUp按钮的Id永远是这个
            case android.R.id.home:
                //兼容xml中定义的行为
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:
                //Toast.makeText(MainActivity.this,"You Clicked fab",Toast.LENGTH_SHORT).show();
                //创建一个Snackbar对象来显示,利用传进的View自动寻找最外层的布局，用于展示Snackbar,第二个参数是内容，然后setAction让用户可以交互
                Snackbar.make(view,"Data deleted",Snackbar.LENGTH_SHORT)
//                        .setAction("Undo", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Toast.makeText(MainActivity.this,"Data restored",Toast.LENGTH_SHORT).show();
//                            }
//                        })并不能设置多个Action
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,"Data deleted",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
            default:
                break;
        }
    }
    private void initFruits(){
        fruitList.clear();
        for(int i=0;i<50;i++){
            Random random=new Random();
            int index=random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }
    //下拉刷新
    private void refreshFruits(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //让线程沉睡两秒假装刷新时间
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //通过该方法切回主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        //通知数据发生变化
                        adapter.notifyDataSetChanged();
                        //表示刷新事件结束
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}

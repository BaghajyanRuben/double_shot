package com.coffee.doubleshot;

import android.content.Intent;
import android.content.pm.ShortcutManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener  {
	private static final int RC_SIGN_IN = 123;

	private TextView mTextMessage;
	private NavigationView navigationView;
	private View headerLayout;
	private Button login;
	private View infoViewContainer;

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener() {

		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			switch (item.getItemId()) {
				case R.id.navigation_home:
					mTextMessage.setText(R.string.title_home);
					return true;
				case R.id.navigation_dashboard:
					mTextMessage.setText(R.string.title_dashboard);
					return true;
				case R.id.navigation_notifications:
					mTextMessage.setText(R.string.title_notifications);
					return true;
			}
			return false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTextMessage = findViewById(R.id.message);
		BottomNavigationView navigation = findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);



		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

		navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		headerLayout = navigationView.getHeaderView(0);


		login = headerLayout.findViewById(R.id.btn_nav_header_login);
		infoViewContainer = headerLayout.findViewById(R.id.info_container);

		FirebaseAuth auth = FirebaseAuth.getInstance();
		if (auth.getCurrentUser() != null) {
			// already signed in
//			UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
//					.setDisplayName("ruben")
//					.setPhotoUri(Uri.parse("https://cdn2.iconfinder.com/data/icons/rcons-user/32/male-shadow-fill-circle-512.png"))
//					.build();
//			auth.getCurrentUser().updateProfile(changeRequest);
//			auth.getCurrentUser().updateEmail("rubenbaghajyan@gmail.com");
			System.out.println(auth.getCurrentUser().getPhoneNumber());
			System.out.println(auth.getCurrentUser().getDisplayName());
			System.out.println(auth.getCurrentUser().getPhotoUrl());
			System.out.println(auth.getCurrentUser().getEmail());
			initLogInState();
		} else {
			// not signed in
			initSignOutState();
		}
	}

	private void initLogInState(){
		FirebaseAuth auth = FirebaseAuth.getInstance();
		FirebaseUser user = auth.getCurrentUser();

		infoViewContainer.setVisibility(View.VISIBLE);
		login.setVisibility(View.GONE);
		SimpleDraweeView avatar = headerLayout.findViewById(R.id.avatar);
		avatar.setImageURI(user.getPhotoUrl());
		TextView name = headerLayout.findViewById(R.id.tv_name);
		TextView phone = headerLayout.findViewById(R.id.tv_phone);
		TextView email = headerLayout.findViewById(R.id.tv_email);

		name.setText(user.getDisplayName());
		phone.setText(user.getPhoneNumber());
		if (!TextUtils.isEmpty(user.getEmail())) {
			email.setText(user.getEmail());
		} else {
			email.setVisibility(View.GONE);
		}
	}

	private void initSignOutState(){
		View infoViewContainer = headerLayout.findViewById(R.id.info_container);
		infoViewContainer.setVisibility(View.VISIBLE);

		infoViewContainer.setVisibility(View.GONE);
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				openSignIn();
			}
		});
		login.setVisibility(View.VISIBLE);
	}

	private void openSignIn(){
		startActivityForResult(
				AuthUI.getInstance()
						.createSignInIntentBuilder()
						.setAvailableProviders(
								Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()))
						.build(),
				RC_SIGN_IN);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	}


	@Override
	public void onBackPressed() {
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.nav_settings) {
			// Handle the camera action
		} else if (id == R.id.nav_edit_profile) {

		}else if (id == R.id.nav_sign_out) {
			FirebaseAuth.getInstance().signOut();
			initSignOutState();
		}

		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}

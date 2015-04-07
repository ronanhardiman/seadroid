package com.seafile.seadroid2.cameraupload;

import android.view.View;
import android.widget.ImageView;
import com.google.common.collect.Lists;
import com.seafile.seadroid2.R;
import com.seafile.seadroid2.data.SeafRepo;
import com.seafile.seadroid2.ui.adapter.ReposAdapter;

import java.util.List;

/**
 * Cloud library adapter
 */
public class CloudLibraryAdapter extends ReposAdapter {

    protected List<SeafRepo> repos = Lists.newArrayList();

    public CloudLibraryAdapter(boolean onlyShowWritableRepos, String encryptedRepoId) {
        super(onlyShowWritableRepos, encryptedRepoId);
    }

    @Override
    protected int getChildLayout() {
        return R.layout.cuc_repo_list_item;
    }

    @Override
    protected int getChildTitleId() {
        return R.id.cuc_repo_list_item_title;
    }

    @Override
    protected int getChildSubTitleId() {
        return R.id.cuc_repo_list_item_subtitle;
    }

    @Override
    protected int getChildIconId() {
        return R.id.cuc_repo_list_item_icon;
    }

    @Override
    protected int getChildActionId() {
        return R.id.cuc_repo_list_item_action;
    }

    @Override
    protected SeafRepo getChildSeafRepo(int position) {
        return repos.get(position);
    }

    @Override
    public boolean isEnabled(int position) {
        // if repo is encrypted, disable it
        // because camera upload service doesn`t support it
        return !(repos.get(position).encrypted);
    }

    @Override
    protected void showRepoSelectedIcon(int position, ImageView imageView) {
        if (checkedRepo == null) {
            imageView.setVisibility(View.INVISIBLE);
            return;
        }

        if (checkedRepo.equals(repos.get(position)))
            imageView.setVisibility(View.VISIBLE);
        else
            imageView.setVisibility(View.INVISIBLE);
    }


    public void setRepos(List<SeafRepo> repoList) {
        repos.clear();
        for (SeafRepo repo : repoList) {
            if (onlyShowWritableRepos && !repo.hasWritePermission()) {
                continue;
            }
            if (encryptedRepoId != null && !repo.id.equals(encryptedRepoId)) {
                continue;
            }
            repos = repoList;
        }
        notifyDataSetChanged();
    }

    public static SeafRepo checkedRepo;
    public void setRepo(SeafRepo repo, boolean isChecked) {
        if (!repos.contains(repo))
            repos.add(repo);

        // only check one item
        if (isChecked)
            checkedRepo = repo;

        //notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return repos.size();
    }

    @Override
    public boolean isEmpty() {
        return repos.isEmpty();
    }

    public void add(SeafRepo repo) {
        repos.add(repo);
    }

    @Override
    public SeafRepo getItem(int position) {
        return repos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}

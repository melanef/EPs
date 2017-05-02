Rails.application.routes.draw do
  devise_for :customers
  devise_for :users

  root to: 'welcome#index'

  resources :theaters do
    resources :rooms do
      resources :sessions
    end
    resources :sessions
  end

  resources :baskets, only: :show
  post '/baskets/:id', to: 'baskets#confirm'
  get '/baskets/:id/finished', to: 'baskets#finished'

  get '/movie/sessions/:id/choose_seat', to: 'sessions#choose_seat'
  post '/movie/sessions/:id/choose_seat', to: 'sessions#buy'

  namespace :admin do
    root 'welcome#index'

    resources :theaters do
      resources :rooms do
        resources :sessions
      end
    end

    resources :movies do
      resources :sessions
    end

    resources :sessions
  end
end

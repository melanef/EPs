class Admin::WelcomeController < Admin::ApplicationController
  def index
    user = current_user
    @user_name = user.name
  end
end

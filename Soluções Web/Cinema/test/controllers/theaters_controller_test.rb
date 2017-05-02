require 'test_helper'

class TheatersControllerTest < ActionController::TestCase
  test "should get index" do
    get :index
    assert_response :success
  end

end
